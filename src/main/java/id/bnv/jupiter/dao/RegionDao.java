package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.*;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class RegionDao extends Dao {

    @Autowired
    public RegionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Region> getAllRegions() {
        Session session = getSession();
        return (List<Region>) session.createQuery("from Region").list();
    }

    public Set<Provider> getAllProvidersByRegionId(int regionId) {
        Query query = getSession().createQuery("from Tarif u where u.regionId=:regionId");
        query.setParameter("regionId", regionId);
        List<Tarif> tarifList = query.list();
        List<Provider> providers = new ArrayList<>();
        for (Tarif tarif : tarifList) {
            Query queryForProviderId = getSession().createQuery("from TarifInfo u where u.tarifInfoId=:tarifInfoId");
            queryForProviderId.setParameter("tarifInfoId", tarif.tarifInfoId.tarifInfoId);
            List<TarifInfo> tarifInfoList = queryForProviderId.list();
            TarifInfo tarifInfo = tarifInfoList.get(0);
            int providerId = tarifInfo.providerId;
            Provider provider = getSession().get(Provider.class, providerId);
            providers.add(provider);
        }
        return new HashSet<>(providers);
    }
}
