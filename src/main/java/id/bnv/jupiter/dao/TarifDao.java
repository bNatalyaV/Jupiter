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
@SuppressWarnings(value = {"unchecked"})
public class TarifDao extends Dao {

    @Autowired
    public TarifDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Tarif getTarifByNumber(PhoneNumber phoneNumber) {
        Query query = getSession().createQuery("from PhoneNumber u where u.tarifId=:tarifId");
        query.setParameter("tarifId", phoneNumber.tarifId);
        List<Tarif> listOfTarif = query.list();
        return listOfTarif.isEmpty() ? null : listOfTarif.get(0);
    }

    //
//    public Tarif addTarifForNumber(PhoneNumber number, int idTarif) {
//        number.tarifId = idTarif;
//        getSession().saveOrUpdate();
//        create(tarif);
//        return tarif;
//    }
    public void addTarifToNumber(int idNumber, int idTarif) {
        PhoneNumber phoneNumber = getSession().get(PhoneNumber.class, idNumber);
        phoneNumber.tarifId = idTarif;
        update(phoneNumber);
    }


    public boolean hasTariff(PhoneNumber phoneNumber) {
        Query query = getSession().createQuery("from PhoneNumber u where u.tarifId=:idtarif");
        query.setParameter("idtarif", phoneNumber.tarifId);
        List<Integer> list = query.list();
        int tarifid = phoneNumber.tarifId;
        //if (tarifid.equals(null))
        if (list.get(0) == null) return false;
        else return true;
    }

    public Journey getJourney(int idPhoneNumber) {
        Query query = getSession().createQuery("from Journey u where u.phoneNumberId=:phoneNumberId");
        query.setParameter("phoneNumberId", idPhoneNumber);
        List<Journey> journeys = query.list();
        return journeys.get(0);
    }

    //add
    public boolean changeTariff(int idnumber, int idTariff) {
        PhoneNumber number = getSession().get(PhoneNumber.class, idnumber);
        Date dateOfStartJourney = new Date();
        Journey journey = new Journey(dateOfStartJourney, idnumber, number.tarifId, idTariff);
        create(journey);
        if (number.balance >= 0) {
            number.tarifId = idTariff;
            update(number);
            journey.endDate = new Date();
            update(journey);
            return true;
        } else {
            JourneyTask journeyTask = new JourneyTask(journey.journeyId, 2, new Date());
            create(journeyTask);
            return false;
        }
    }

    public TarifInfo getTarifInfoByProviderId(int providerId) {
        Query query = getSession().createQuery("from TarifInfo u where u.providerId=:providerId");
        query.setParameter("providerId", providerId);
        List<TarifInfo> tarifInfoList = query.list();
        TarifInfo tarifInfo = tarifInfoList.get(0);
        return tarifInfo;
    }

    public Tarif getTarifByTarifInfoId(int tarifIndoId) {
        Query query = getSession().createQuery("from Tarif u where u.tarifInfoId=:tarifInfoId");
        query.setParameter("tarifInfoId", tarifIndoId);
        List<Tarif> tarifs = query.list();
        return tarifs.get(0);
    }

    public Provider getProviderByTarifId(int tarifId) {
        Session session = getSession();
        Tarif tarif = session.get(Tarif.class, tarifId);
        TarifInfo tarifInfo = session.get(TarifInfo.class, tarif.tarifInfoId);
        Provider provider = session.get(Provider.class, tarifInfo.providerId);
        return provider;
    }

    //6 request
    public TarifOffering getTarifOffering(int offeringId) {
        Query query = getSession().createQuery("from TarifOffering u where u.offeringId=:offeringId");
        query.setParameter("offeringId", offeringId);
        List<TarifOffering> list = query.list();
        TarifOffering tarifOffering = list.get(0);
        return tarifOffering;
    }

    //vk request
    public FullInfoAboutTarif getFullInfoAboutTarif(int numberId, int nextTarifId) {
        Session session = getSession();
        PhoneNumber phoneNumber = session.get(PhoneNumber.class, numberId);
        Tarif currentTarif = session.get(Tarif.class, phoneNumber.tarifId);
        TarifInfo currentTarifInfo = session.get(TarifInfo.class, currentTarif.tarifInfoId);
        Provider currentProvider = getProviderByTarifId(currentTarif.tarifId);
        Date date = new Date();
        Tarif newTarif = session.get(Tarif.class, nextTarifId);
        TarifInfo newTarifInfo = session.get(TarifInfo.class, newTarif.tarifInfoId);
        Provider newProvider = getProviderByTarifId(nextTarifId);
        FullInfoAboutTarif info = new FullInfoAboutTarif(currentProvider.providerName, currentTarifInfo.tarifName,
                phoneNumber.phoneNumber, date, newProvider.providerName, newTarifInfo.tarifName);
        return info;
    }

    //for Vlad
    public List<TariffNameIdPrice> getTariffNameIdPrice(int regionId, int providerId) {
        Query query = getSession().createQuery("from Tarif t where t.regionId=:id");
        query.setParameter("id", regionId);
        List<Tarif> tarifs = query.list();
        List<TariffNameIdPrice> tariffNameIdPriceList = new ArrayList<>();
        Iterator<Tarif> tarifIterator = tarifs.iterator();
        while (tarifIterator.hasNext()) {
            Tarif tarif = tarifIterator.next();
            if (tarif.tarifInfoId.providerId != providerId) tarifIterator.remove();
        }
        for (Tarif tarif: tarifs) {
            tariffNameIdPriceList.add(new TariffNameIdPrice(tarif.tarifId, tarif.tarifInfoId.tarifName, tarif.tarifPrice));
        }
        return tariffNameIdPriceList;

//        List<TariffNameIdPrice> list = new ArrayList<>();
//        Query queryForTariffName = getSession().createQuery("from TarifInfo u where u.providerId=:providerId");
//        queryForTariffName.setParameter("providerId", providerId);
//        List<TarifInfo> tarifInfoList = queryForTariffName.list();
//        for (int i = 0; i < tarifInfoList.size(); i++) {
//            TarifInfo tarifInfo = tarifInfoList.get(i);//can get name
//            Query queryForTariffIdPrice = getSession().createQuery("from Tarif u where u.tarifInfoId=:tarifInfoId");
//            queryForTariffIdPrice.setParameter("tarifInfoId", tarifInfo.tarifInfoId);
//            List<Tarif> tarifs = queryForTariffIdPrice.list();
//            Tarif tarif = tarifs.get(0);
//            list.add(new TariffNameIdPrice(tarif.tarifId, tarifInfo.tarifName, tarif.tarifPrice));
//        }
//        return list;
//        Query query=getSession().createSQLQuery("SELECT p FROM jupiter.tariff p, jupiter.tariff_inf WHERE");
//
//        Query qry= getSession().createQuery("select t.tarifId, t.regionId, t.tarifInfoId,t.tarifPrice from Tarif t "
//                + "left join c.items i");
//        List l = qry.list();
//
//        tarifId;
//
//        public int regionId;
//
//        public int tarifInfoId;
//
//        public String tarifPrice;
    }
}
