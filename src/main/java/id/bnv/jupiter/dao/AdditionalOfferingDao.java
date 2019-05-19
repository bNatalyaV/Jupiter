package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class AdditionalOfferingDao extends Dao {

    @Autowired
    public AdditionalOfferingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<AdditionalOffering> getAllOffering() {
        return getSession()
                .createQuery("from AdditionalOffering")
                .list();
    }

    // not using now - remove if not needed when see this next time
//    public List<TariffAndOfferings> getAdditionalOfferings(int regionId, int providerId) {
//        List<TariffAndOfferings> list = new ArrayList<>();
//        Query queryForTarif = getSession().createQuery("from Tarif u where u.regionId=:regionId");
//        queryForTarif.setParameter("regionId", regionId);
//        List<Tarif> tariffs = queryForTarif.list();
//        for (int i = 0; i < tariffs.size(); i++) {
//            Tarif tarif = tariffs.get(i);
//            TarifInfo tarifInfo = getSession().get(TarifInfo.class, tarif.tarifInfoId);
//            Query queryForTariffOfferings = getSession().createQuery("from TarifOffering u where u.tarifId=:odin");
//            queryForTariffOfferings.setParameter("odin", tarif.tarifId);
//            List<TarifOffering> offerings = queryForTariffOfferings.list();
//            List<TariffOfferings> tariffOfferingsList = new ArrayList<>();
//            for (int k = 0; k < offerings.size(); k++) {
//                TarifOffering offering = offerings.get(k);// осталось щф нэйм и деск
//                Query queryForAdditionalOfferings = getSession().createQuery("from AdditionalOffering u where u.offeringId=:offeringId");
//                queryForAdditionalOfferings.setParameter("offeringId", offering.offeringId);
//                List<AdditionalOffering> additionalOfferings = queryForAdditionalOfferings.list();
//                AdditionalOffering additionalOffering = additionalOfferings.get(0);
//                TariffOfferings tariffOfferings = new TariffOfferings(offering.tarifId, offering.offeringId,
//                        offering.offeringPrice, offering.quantity, additionalOffering.offeringName,
//                        additionalOffering.offeringDesc);
//                tariffOfferingsList.add(tariffOfferings);
//            }
//            TariffAndOfferings tariffAndOfferingsForOneTariff = new TariffAndOfferings(tarif.tarifId,
//                    tarifInfo.tarifName, tarif.tarifPrice, tariffOfferingsList);
//            list.add(tariffAndOfferingsForOneTariff);
//        }
//        return list;
//    }
}
