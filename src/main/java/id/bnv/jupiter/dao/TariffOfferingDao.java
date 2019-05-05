package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.AdditionalOffering;
import id.bnv.jupiter.pojo.OffersAndAddOffers;
import id.bnv.jupiter.pojo.TarifOffering;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
public class TariffOfferingDao extends Dao{

    @Autowired
    public TariffOfferingDao(SessionFactory sessionFactory) {super(sessionFactory);}

    public List<OffersAndAddOffers> getOffersAndAddOffers(int tarifId){
        Query queryForTariffOfferings=getSession().createQuery("from TarifOffering u where u.tarifId=:tarifId");
        queryForTariffOfferings.setParameter("tarifId",tarifId);
        List<TarifOffering> tariffOfferings =queryForTariffOfferings.list();
//        List<TarifOffering> tariffOfferings = a
//                .stream()
//                .filter(tarifOffering -> tarifOffering.offeringPrice != null)
//                .collect(Collectors.toList());
        Iterator<TarifOffering> iterator=tariffOfferings.iterator();
        while(iterator.hasNext()){
            TarifOffering tarifOffering=iterator.next();
            if (tarifOffering.offeringPrice == null) iterator.remove();
        }
        List<OffersAndAddOffers> offersAndAddOffers=new ArrayList<>();
        for (int i = 0; i< tariffOfferings.size(); i++) {
            TarifOffering tariffOffering = tariffOfferings.get(i);
            AdditionalOffering additionalOffering=getSession().get(AdditionalOffering.class, tariffOffering.offeringId);
            offersAndAddOffers.add(new OffersAndAddOffers(tariffOffering.offeringId, tariffOffering.offeringPrice,
                    tariffOffering.quantity, additionalOffering.offeringName, additionalOffering.offeringDesc));
        }
        return offersAndAddOffers;
    }

    public List<OffersAndAddOffers> getOfferNamePriceQuantity(int tariffId) {
        Query queryForTariffOffering=getSession().createQuery("from TarifOffering u where u.tarifId=:tarifId");
        queryForTariffOffering.setParameter("tarifId", tariffId);
        List<TarifOffering> tarifOfferingList=queryForTariffOffering.list();
        List<OffersAndAddOffers> list=new ArrayList<>();
        for (int i=0; i<tarifOfferingList.size(); i++) {
            TarifOffering tarifOffering=tarifOfferingList.get(i);
            Query queryForNameOffering=getSession().createQuery("from AdditionalOffering u where u.offeringId=:offeringId");
            queryForNameOffering.setParameter("offeringId", tarifOffering.offeringId);
            List<AdditionalOffering> additionalOfferingsList=queryForNameOffering.list();
            AdditionalOffering additionalOffering=additionalOfferingsList.get(0);
            list.add(new OffersAndAddOffers(tarifOffering.offeringPrice, tarifOffering.quantity, additionalOffering.offeringName));
        }
        return list;
    }

}
