package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        for (int i=1; i<=12; i++) {
            Journey journey=getSession().get(Journey.class, i);
            journey.oldTariffId=1;
            update(journey);
        }
    }

    //Все journeys завершенные и не только, история переходов
    public List<FullInfoAboutTarif> getInfoAboutJourneys(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        List<FullInfoAboutTarif> list = new ArrayList<>();
        for (PhoneNumber number :
                numbers) {
            List<Journey> journeys = getJourneysByNumberId(number.id);
            for (Journey journey: journeys) {
                Tarif oldTarif=getSession().get(Tarif.class, journey.oldTariffId);
                Provider oldProvider=getSession().get(Provider.class, oldTarif.tarifInfoId.providerId);
                Tarif newTarif=getSession().get(Tarif.class, journey.tarifId);
                Provider newProvider=getSession().get(Provider.class, newTarif.tarifInfoId.providerId);
                list.add(new FullInfoAboutTarif(oldProvider.providerName, oldTarif.tarifInfoId.tarifName,
                        number.phoneNumber, journey.startDate,
                        newProvider.providerName, newTarif.tarifInfoId.tarifName));
            }
        }
        return list;
    }

    public Journey addJourney(int numberId, int tariffId) {
        Date date = new Date();
        Journey newJourney = new Journey(new Date(), numberId, tariffId);
        create(newJourney);
        return newJourney;
    }

    public Set<PhoneNumber> getCompletedNumbers(int userId) {
        List<PhoneNumber> numbers = numberDao.getAllNumbersOfUser(userId);
        Set<PhoneNumber> listForReturn = new HashSet<>();
        for (PhoneNumber number : numbers) {
            List<Journey> journeysForNumber = getJourneysByNumberId(number.id);
            for (Journey journey : journeysForNumber) {
                if (!(journey.endDate.equals(null))) listForReturn.add(number);
            }
        }
        return listForReturn;
    }
}
