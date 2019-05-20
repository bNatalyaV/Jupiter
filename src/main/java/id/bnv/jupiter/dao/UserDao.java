package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.User;
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
public class UserDao extends Dao {

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User getUser(int id) {
        return getSession().get(User.class, id);
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

    public boolean checkEmailExist(String email, int id) {
        List list = getSession()
                .createQuery("from User u where u.id != :id and u.email = :email")
                .setParameter("id", id)
                .setParameter("email", email)
                .list();
        return !list.isEmpty();
    }

    public boolean checkLoginExist(String login, int id) {
        List list = getSession()
                .createQuery("from User u where u.id != :id and u.login = :login")
                .setParameter("id", id)
                .setParameter("login", login)
                .list();
        return !list.isEmpty();
    }

    public boolean checkPassportExist(String passport, int id) {
        List list = getSession()
                .createQuery("from User u where u.id != :id and u.passport = :passport")
                .setParameter("id", id)
                .setParameter("passport", passport)
                .list();
        return !list.isEmpty();
    }

    public User getUserByLogin(String login) {
        List<User> users = (List<User>) getSession()
                .createQuery("from User u where u.login = :login")
                .setParameter("login", login)
                .list();

        if (users.isEmpty()) return null;
        return users.get(0);
    }
}

