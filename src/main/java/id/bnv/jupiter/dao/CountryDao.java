package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Country;
import id.bnv.jupiter.pojo.Region;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class CountryDao extends Dao {

    @Autowired
    public CountryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Country> getAllCountries() {
        Session session = getSession();
        return (List<Country>) session.createQuery("from Country").list();
    }

    public List<Region> getAllRegionsForCountry(int idCountry) {
        Query query = getSession().createQuery("from Region u where u.countryId=:countryId");
        query.setParameter("countryId", idCountry);
        return (List<Region>) query.list();
    }
}