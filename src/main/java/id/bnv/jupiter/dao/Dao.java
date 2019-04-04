package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings({"unchecked"})
public class Dao {
    private final SessionFactory sessionFactory;

    @Autowired
    public Dao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void create(Object object) {
        Session session = getSession();
        session.save(object);
    }

    public void update(Object object) {
        getSession().update(object);
    }

    public void delete(Object object) {
        getSession().delete(object);
    }
}
