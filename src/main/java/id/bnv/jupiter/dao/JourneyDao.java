package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Transactional
public class JourneyDao extends Dao {
    private final NumberDao numberDao;

    @Autowired
    public JourneyDao(SessionFactory sessionFactory, NumberDao numberDao) {
        super(sessionFactory);
        this.numberDao = numberDao;
    }

    public Journey getJourney(int idJourney) {
        Session session = getSession();
        Journey journey = session.get(Journey.class, idJourney);
        return journey;
    }

    public List<Journey> getJourneysByNumberId(int numberId) {
        Query queryForJourney = getSession()
                .createQuery("from Journey j where j.phoneNumberId=:phoneNumberId")
                .setParameter("phoneNumberId", numberId);
        List<Journey> journeys = queryForJourney.list();
        return journeys;
    }

    public void fullOldTariffId() {
        for (int i = 1; i <= 12; i++) {
            Journey journey = getSession().get(Journey.class, i);
            journey.oldTariffId = 1;
            update(journey);
        }
    }

    // add journeyId
    //Все journeys завершенные и не только, история переходов
    public List<FullInfoAboutTarif> getInfoAboutJourneys(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<FullInfoAboutTarif> list = new ArrayList<>();
        for (PhoneNumber number :
                numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey : journeys) {
                Tarif oldTarif = getSession().get(Tarif.class, journey.oldTariffId);
                Provider oldProvider = getSession().get(Provider.class, oldTarif.tarifInfoId.providerId);
                Tarif newTarif = getSession().get(Tarif.class, journey.tarifId);
                Provider newProvider = getSession().get(Provider.class, newTarif.tarifInfoId.providerId);
                list.add(new FullInfoAboutTarif(oldProvider.providerName, oldTarif.tarifInfoId.tarifName,
                        number.phoneNumber, journey.startDate,
                        newProvider.providerName, newTarif.tarifInfoId.tarifName,
                        journey.journeyId, number.id, newTarif.tarifId));
            }
        }
        return list;
    }

    public String addJourney(int numberId, int tariffId) throws Exception {
        PhoneNumber phoneNumber = getSession().get(PhoneNumber.class, numberId);
        Journey newJourney = new Journey(new Date(), numberId, phoneNumber.tarifId, tariffId);
        create(newJourney);
        JourneyTask journeyTask1 = new JourneyTask(newJourney.journeyId, 1, new Date());
        create(journeyTask1);
        Thread.sleep(2000);
        journeyTask1.taskfinish = new Date();
        update(journeyTask1);

        //add second task in 5 seconds
        JourneyTask journeyTask2 = new JourneyTask(newJourney.journeyId, 2, new Date());
        getSession().merge(journeyTask2);
        if (phoneNumber.balance > 0) {
            journeyTask2.taskfinish = new Date();
            return "Ok";
        } else return "Balance is less than zero";
    }

    public void addTask2(int numberId, int tariffId, int journeyId) throws Exception{
        Journey journey = getSession().get(Journey.class, journeyId);
        JourneyTask journeyTask2= (JourneyTask) getSession()
                .createQuery("from JourneyTask jt where jt.journeyId=:journeyId and jt.taskId=:id ")
                .setParameter("journeyId",journeyId)
                .setParameter("id", 2)
                .list()
                .get(0);
        if (journeyTask2.taskfinish == null) {
            journeyTask2.taskfinish=new Date();
            update(journeyTask2);
        }
        for (int i=3; i<7; i++) {
            JourneyTask journeyTask = new JourneyTask(journeyId, i, new Date());
            create(journeyTask);
            Thread.sleep(3000);
            journeyTask.taskfinish = new Date();
            getSession().update(journeyTask);
        }
        journey.endDate=new Date();
        journey.tarifId=tariffId;
        PhoneNumber number=getSession().get(PhoneNumber.class, numberId);
        number.tarifId=tariffId;
        update(number);
    }

    public List<PhoneNumber> getCompletedNumbers(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
//        Iterator<PhoneNumber> phoneNumberIterator = numbers.iterator();
//        while (phoneNumberIterator.hasNext()) {
//            PhoneNumber number1 = phoneNumberIterator.next();
//            List<Journey> journeysForNumber = getJourneysByNumberId(number1.id);
//            for (Journey journey : journeysForNumber) {
//                if (journey.endDate == null && journey.startDate != null)
//                    phoneNumberIterator.remove();
//            }
//        }

        List<PhoneNumber> n = numbers.stream()
                .filter(new Predicate<PhoneNumber>() {
                    @Override
                    public boolean test(PhoneNumber phoneNumber) {
                        List<Journey> journeysByNumberId = getJourneysByNumberId(phoneNumber.id);
                        for (Journey journey : journeysByNumberId) {
                            if (journey.endDate == null && journey.startDate != null)
                                return false;
                        }
                        return true;
                    }
                }).collect(Collectors.toList());

        return n;
    }

    public List<Journey> getCompletedJourneys(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<Journey> list = new ArrayList<>();
        for (PhoneNumber number : numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey : journeys) {
                if (journey.endDate != null) list.add(journey);
            }
        }
        return list;
    }

    public List<PhoneNumber> getUncompletedJourneys(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<PhoneNumber> uncompletedNumbers = new ArrayList<>();
        for (PhoneNumber number : numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey : journeys) {
                if (journey.endDate == null) uncompletedNumbers.add(number);
            }
        }
        return uncompletedNumbers;
    }
}