package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.City;
import id.bnv.jupiter.pojo.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CityDao extends Dao {

    @Autowired
    public CityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<City> getAllCity() {
        Session session = getSession();
        List<City> cityList = session.createQuery("from Country").list();

        return cityList;
    }

    public List<City> getCityByRegionId(int regionId) {
        List<City> cities = getSession()
                .createQuery("from City c where c.regionId=:regionId")
                .setParameter("regionId", regionId)
                .list();

        return cities;
    }
}
