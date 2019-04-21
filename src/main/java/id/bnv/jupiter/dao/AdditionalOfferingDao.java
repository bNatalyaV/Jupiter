package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.AdditionalOffering;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class AdditionalOfferingDao extends Dao {

    @Autowired
    public AdditionalOfferingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<AdditionalOffering> getAllOffering() {
        List list = getSession()
                   .createQuery("from AdditionalOffering")
                   .list();
        return list;
    }
}
