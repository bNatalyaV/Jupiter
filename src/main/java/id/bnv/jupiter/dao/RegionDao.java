package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Provider;
import id.bnv.jupiter.pojo.Region;
import id.bnv.jupiter.pojo.Tarif;
import id.bnv.jupiter.pojo.TarifInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RegionDao extends Dao {

    @Autowired
    public RegionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Region> getAllRegions() {
        Session session = getSession();
        List<Region> regionList = session.createQuery("from Region").list();
        return regionList;
    }

    public List<String> getAllProvidersByRegionId(int regionId) {
        Query query = getSession().createQuery("from Tarif u where u.regionId=:regionId");
        query.setParameter("regionId", regionId);
        List<Tarif> regionList = query.list();
        List<Integer> providersId = new ArrayList<>();
        for (int i = 0; i < regionList.size(); i++) {
            Tarif tarif = regionList.get(i);
            Query queryForProviderId = getSession().createQuery("from TarifInfo u where u.tarifInfoId=:tarifInfoId");
            queryForProviderId.setParameter("tarifInfoId", tarif.tarifInfoId);
            List<TarifInfo> tarifInfoList = queryForProviderId.list();
            TarifInfo tarifInfo = tarifInfoList.get(0);
            int providerId = tarifInfo.providerId;
            providersId.add(providerId);
        }
        List<String> namesOfProviders = new ArrayList<>();
        Session session = getSession();
        for (int providerId : providersId) {
            Provider provider = session.get(Provider.class, providerId);
            String nameOfProvider = provider.providerName;
            namesOfProviders.add(nameOfProvider);
        }
        return namesOfProviders;
    }

    /*
            Query query = getSession().createQuery("select Tarif from Tarif u, TarifInfo ti where u.regionId=:regionId and u.tariff_inf_id=ti.tariff_inf.id");
        query.setParameter("regionId", regionId);
        List<Tarif> regionList = query.list();
        List<Integer> providersId = new ArrayList<>();
        for (int i = 0; i < regionList.size(); i++) {
            Tarif tarif = regionList.get(i);
            Query queryForProviderId = getSession().createQuery("from TarifInfo u where u.tarifInfoId=:tarifInfoId");
            queryForProviderId.setParameter("tarifInfoIdId", tarif.tarifInfoId);
            List<TarifInfo> tarifInfoList = queryForProviderId.list();
            TarifInfo tarifInfo = tarifInfoList.get(0);
            int providerId = tarifInfo.providerId;
            providersId.add(providerId);
        }
        List<String> namesOfProviders = new ArrayList<>();
        Session session = getSession();
        for (int providerId : providersId) {
            Provider provider = session.get(Provider.class, providerId);
            String nameOfProvider = provider.providerName;
            namesOfProviders.add(nameOfProvider);
        }
        return namesOfProviders;
    }
   */
}
