package id.bnv.jupiter.dao;

import id.bnv.jupiter.Exeption.UserException;
import id.bnv.jupiter.pojo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
@SuppressWarnings({"unchecked"})
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

    public FullInfoAboutTarif getFullInfoByJourneyId(int journeyId) {
        Journey journey = getSession().get(Journey.class, journeyId);
        PhoneNumber number = getSession().get(PhoneNumber.class, journey.phoneNumberId);
        Tarif oldTarif = getSession().get(Tarif.class, journey.oldTariffId);
        Provider oldProvider = getSession().get(Provider.class, oldTarif.tarifInfoId.providerId);
        Tarif newTarif = getSession().get(Tarif.class, journey.tarifId);
        Provider newProvider = getSession().get(Provider.class, newTarif.tarifInfoId.providerId);
        return new FullInfoAboutTarif(oldProvider.providerName, oldTarif.tarifInfoId.tarifName,
                number.phoneNumber, journey.startDate,
                newProvider.providerName, newTarif.tarifInfoId.tarifName,
                journey.journeyId, number.id, newTarif.tarifId, journey.endDate);
    }

    //Все journeys завершенные и не только, история переходов
    public List<FullInfoAboutTarif> getInfoAboutJourneys(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<FullInfoAboutTarif> list = new ArrayList<>();
        for (PhoneNumber number :
                numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey : journeys) {
                FullInfoAboutTarif fullInfoAboutTarif = getFullInfoByJourneyId(journey.journeyId);
//                Tarif oldTarif = getSession().get(Tarif.class, journey.oldTariffId);
//                Provider oldProvider = getSession().get(Provider.class, oldTarif.tarifInfoId.providerId);
//                Tarif newTarif = getSession().get(Tarif.class, journey.tarifId);
//                Provider newProvider = getSession().get(Provider.class, newTarif.tarifInfoId.providerId);
                list.add(fullInfoAboutTarif);
            }
        }
        return list;
    }

    public void fillInTask(int journeyId, int i, Session session) throws InterruptedException {
        session.beginTransaction();
        JourneyTask journeyTask = new JourneyTask(journeyId, i, new Date());
        create(journeyTask);
        session.flush();
        session.getTransaction().commit();
        Thread.sleep(5000);
        session.beginTransaction();
        journeyTask.taskfinish = new Date();
        update(journeyTask);
        session.flush();
        session.getTransaction().commit();
    }

    public void completeJourney(Journey journey, int tariffId, int numberId, Session session) throws Exception {
        Thread.sleep(2000);
        journey.endDate = new Date();
        journey.tarifId = tariffId;
        PhoneNumber number = session.get(PhoneNumber.class, numberId);
        number.tarifId = tariffId;
        session.beginTransaction();
        update(journey);
        update(number);
    }

    public void addJourney(int numberId, int tariffId) throws Exception {
        Session session = getSession();
        PhoneNumber phoneNumber = getSession().get(PhoneNumber.class, numberId);
        Journey newJourney = new Journey(new Date(), numberId, phoneNumber.tarifId, tariffId);
        create(newJourney);
        session.flush();
        session.getTransaction().commit();
        fillInTask(newJourney.journeyId, 1, session);
        session.beginTransaction();
        JourneyTask journeyTask2 = new JourneyTask(newJourney.journeyId, 2, new Date());
        create(journeyTask2);
        session.flush();
        session.getTransaction().commit();
        if (phoneNumber.balance > 0) {
            journeyTask2.taskfinish = new Date();
            for (int i = 3; i < 7; i++) {
                fillInTask(newJourney.journeyId, i, session);
            }
            completeJourney(newJourney, tariffId, numberId, session);
        } else throw new UserException("Balance is less than zero");
    }

    public void addTaskFrom2To6(int numberId, int tariffId, int journeyId) throws Exception {
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
            session.flush();
            session.getTransaction().commit();
            Thread.sleep(3000);
        }

        for (int i = 3; i < 7; i++) {
            fillInTask(journeyId, i, session);
        }
        completeJourney(journey, tariffId, numberId, session);
    }

    public List<JourneyTask> getJourneyTasksListByJourneyId(int journeyId) {
        List<JourneyTask> list = getSession()
                .createQuery("from JourneyTask jt where jt.journeyId=:journeyId")
                .setParameter("journeyId", journeyId)
                .list();
        return list;
    }

    public String getNameOfTask(int taskId) {
        Task task = getSession().get(Task.class, taskId);
        return task.taskName;

    }

    public List<JourneysAndTasks> getTasksByUserId(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<JourneysAndTasks> list = new ArrayList<>();
        for (PhoneNumber number : numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey : journeys) {
                List<JourneyTask> tasks = getJourneyTasksListByJourneyId(journey.journeyId);
                List<InfoAboutTasks> infoAboutTasksList = new ArrayList<>();
                FullInfoAboutTarif fullInfoAboutTarif1 = getFullInfoByJourneyId(journey.journeyId);
                for (JourneyTask journeyTask : tasks) {
                    String nameOfTask = getNameOfTask(journeyTask.taskId);
                    infoAboutTasksList.add(new InfoAboutTasks(nameOfTask, journeyTask));
                }
                list.add(new JourneysAndTasks(fullInfoAboutTarif1, infoAboutTasksList));
            }
        }
        return list;
    }

    public List<PhoneNumber> getCompletedNumbers(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
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