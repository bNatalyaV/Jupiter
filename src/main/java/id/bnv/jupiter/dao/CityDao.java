package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class CityDao extends Dao {

    @Autowired
    public CityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<City> getAllCity() {
        return (List<City>) getSession().createQuery("from City").list();
    }

    public List<City> getCityByRegionId(int regionId) {
        return (List<City>) getSession()
                .createQuery("from City c where c.regionId=:regionId")
                .setParameter("regionId", regionId)
                .list();
    }
}