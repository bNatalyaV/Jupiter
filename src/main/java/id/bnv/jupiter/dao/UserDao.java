package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.PhoneNumber;
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
public class UserDao extends Dao {

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public User getUser(int id) {
        Session session = getSession();
        User user = session.get(User.class, id);
        return user;
    }

    public User getUserBy(String email) {
        Query query = getSession().createQuery("from User u where u.email=:email");
        query.setParameter("email", email);
        List<User> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<User> getAllUsers() {
        Session session = getSession();
        List<User> users = session.createQuery("from User").list();
        return users;
    }
}
