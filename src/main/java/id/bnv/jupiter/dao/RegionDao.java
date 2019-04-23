package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Region;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RegionDao extends Dao {

    @Autowired
    public RegionDao(SessionFactory sessionFactory) {super(sessionFactory);}

    public List<Region> getAllRegions() {
        Session session=getSession();
        List<Region> regionList=session.createQuery("from Region").list();
        return regionList;
    }

}
