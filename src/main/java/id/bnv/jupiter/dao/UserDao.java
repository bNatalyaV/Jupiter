package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
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

//    public String registerUser(String email, String login, String password) {
//        Session session = getSession();
//        Query queryForEmail = session.createQuery("from User u where u.email=:email")
//                .setParameter("email", email);
//        List<User> userListForEmail = queryForEmail.list();
//        String emailFromDB = userListForEmail.get(0).email;
//        if (emailFromDB.equals(email)) return "Email already existed";
//        else {
//            Query queryForLogin = session.createQuery("from User u where u.login=:login")
//                    .setParameter("login", login);
//            List<User> userListForLogin = queryForLogin.list();
//            String loginFromDB = userListForLogin.get(0).login;
//            if (loginFromDB.equals(login)) return "Login already existed";
//            else {
//                User user=new User(email, login, password);
//                create(user);
//                return "User was created";
//            }
//        }
//    }
    public String registerUser(String email, String login, String password) {
       if (checkEmailOfUser(email))
       {
           if (checkLoginOfUser(login))
           {
               User user=new User(email, login, password);
               create(user);

               return "User was created";
           }
           else
               return "Login already existed";
       }
       else
           return "Email already existed";
    }

    public boolean checkEmailOfUser(String email) {
        Query queryForEmail = getSession().createQuery("from User u where u.email=:email");
        queryForEmail.setParameter("email", email);
        List<User> userListForEmail = queryForEmail.list();
        if(userListForEmail.isEmpty())
            return true;
        else
            return false;
    }
    public boolean checkLoginOfUser(String login) {
        Query queryForLogin = getSession().createQuery("from User u where u.login=:login")
                .setParameter("login", login);
        List<User> userListForLogin = queryForLogin.list();
        if (userListForLogin.isEmpty())
            return true;
        else
            return false;
    }
}

