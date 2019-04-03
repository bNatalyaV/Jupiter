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

    private Session getSession() {
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

    public User getUser(int id) {
        Session session = getSession();
        User user = session.get(User.class, id);
        return user;
    }

    public List<User> getAllUsers() {
        Session session = getSession();
        List<User> users = session.createQuery("from User").list();
        return users;
    }
}
