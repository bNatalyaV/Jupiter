package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Adress;
import id.bnv.jupiter.pojo.ProviderAdress;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class ProviderAdressDao extends Dao {

    @Autowired
    public ProviderAdressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Adress> getAdress(int cityId, int providerId) {
        List<Adress> adresses = new ArrayList<>();
        List<ProviderAdress> providerAdresses = getSession()
                .createQuery("from ProviderAdress a where a.providerId=:providerId")
                .setParameter("providerId", providerId).list();
        for (ProviderAdress providerAdress : providerAdresses) {
            if (providerAdress.adressId.cityId == cityId)
                adresses.add(providerAdress.adressId);
        }
        return adresses;
    }
}
