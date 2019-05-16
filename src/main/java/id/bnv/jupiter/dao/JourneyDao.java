package id.bnv.jupiter.dao;

import id.bnv.jupiter.authentication.Response;
import id.bnv.jupiter.pojo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
public class JourneyDao extends Dao {
    private final NumberDao numberDao;
    private final TarifDao tarifDao;

    @Autowired
    public JourneyDao(SessionFactory sessionFactory, NumberDao numberDao, TarifDao tarifDao) {
        super(sessionFactory);
        this.numberDao = numberDao;
        this.tarifDao = tarifDao;
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
                        journey.journeyId, number.id, newTarif.tarifId, journey.endDate));
            }
        }
        return list;
    }

    public Response addJourney(int numberId, int tariffId) throws Exception {
        Session session = getSession();
        PhoneNumber phoneNumber = getSession().get(PhoneNumber.class, numberId);
        Journey newJourney = new Journey(new Date(), numberId, phoneNumber.tarifId, tariffId);
        create(newJourney);
        JourneyTask journeyTask1 = new JourneyTask(newJourney.journeyId, 1, new Date());
        create(journeyTask1);
        session.flush();
        session.getTransaction().commit();
        Thread.sleep(2000);
        session.beginTransaction();
        journeyTask1.taskfinish = new Date();
        update(journeyTask1);
        session.flush();
        session.getTransaction().commit();
        Thread.sleep(2000);
        session.beginTransaction();
        //add second task in 5 seconds
        JourneyTask journeyTask2 = new JourneyTask(newJourney.journeyId, 2, new Date());
        create(journeyTask2);
        //    session.getTransaction().commit();
        //  session.beginTransaction();
        if (phoneNumber.balance > 0) {
            journeyTask2.taskfinish = new Date();
            return new Response("OK", Response.Status.ok);
        } else return new Response("Balance is less than zero", Response.Status.smthWrong);
    }


    public void addTask2(int numberId, int tariffId, int journeyId) throws Exception {
        Session session = getSession();
        Journey journey = session.get(Journey.class, journeyId);
        JourneyTask journeyTask2 = (JourneyTask) getSession()
                .createQuery("from JourneyTask jt where jt.journeyId=:journeyId and jt.taskId=:id ")
                .setParameter("journeyId", journeyId)
                .setParameter("id", 2)
                .list()
                .get(0);
        if (journeyTask2.taskfinish == null) {
            journeyTask2.taskfinish = new Date();
            update(journeyTask2);
        }
        session.flush();
        session.getTransaction().commit();
        Thread.sleep(3000);

        for (int i = 3; i < 7; i++) {
            session.beginTransaction();
            JourneyTask journeyTask = new JourneyTask(journeyId, i, new Date());
            create(journeyTask);
            Thread.sleep(5000);
            journeyTask.taskfinish = new Date();
            update(journeyTask);
            session.flush();
            session.getTransaction().commit();
        }
        Thread.sleep(2000);
        journey.endDate = new Date();
        journey.tarifId = tariffId;
        PhoneNumber number = session.get(PhoneNumber.class, numberId);
        number.tarifId = tariffId;
        session.beginTransaction();
        update(journey);
        update(number);
    }

    public List<JourneyTask> getJourneyTasksListByJourneyId(int journeyId){
        List<JourneyTask> list=getSession()
                .createQuery("from JourneyTask jt where jt.journeyId=:journeyId")
                .setParameter("journeyId", journeyId)
                .list();
        return list;
    }

    public List<NumberAndListJourneys> getTasksByUserId(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<FullInfoAboutTarif> fullInfoAboutTarifList = getInfoAboutJourneys(userId);
        List<NumberAndListJourneys> list = new ArrayList<>();
        for (PhoneNumber number : numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            List<JourneyAndTasks> journeyAndTasksList = new ArrayList<>();
            for (Journey journey : journeys) {
                List<JourneyTask> tasks = getJourneyTasksListByJourneyId(journey.journeyId);
                for (FullInfoAboutTarif fullInfoAboutTarif : fullInfoAboutTarifList) {
                    if (fullInfoAboutTarif.numberId == number.id && fullInfoAboutTarif.journeyId == journey.journeyId) {
                        FullInfoAboutTarif fullInfoAboutTarif1 = fullInfoAboutTarif;
                        JourneyAndTasks journeyAndTasks = new JourneyAndTasks(fullInfoAboutTarif1, tasks);
                        journeyAndTasksList.add(journeyAndTasks);
                    }
                }
                list.add(new NumberAndListJourneys(number.id, journeyAndTasksList));
            }
        }
        return list;
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