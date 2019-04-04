package id.bnv.jupiter.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TarifDao extends Dao {

   @Autowired
    public TarifDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

//    public TarifDao
}
