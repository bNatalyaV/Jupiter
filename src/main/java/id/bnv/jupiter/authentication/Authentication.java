package id.bnv.jupiter.authentication;

import id.bnv.jupiter.Exeption.UserException;
import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.pojo.ForDecode;
import id.bnv.jupiter.pojo.User;
import id.bnv.jupiter.pojo.UserAndToken;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;
import java.util.List;


@Repository
@Transactional
public class Authentication extends Dao {
    private final IssueAndDecodeToken issueAndDecodeToken;

    @Autowired
    public Authentication(SessionFactory sessionFactory, IssueAndDecodeToken iADT) {
        super(sessionFactory);
        issueAndDecodeToken = iADT;
    }

    //проверка данных,
// регистрация нового пользователя,
// добавление в базу,
// вернуть токен+пользователя
    public UserAndToken registerUser(User user) {
        if (checkEmailOfUser(user.email)) {
            if (checkLoginOfUser(user.login)) {
                User newUser = new User(user.email, user.login, user.password);
                create(newUser);
                String tokenForNewUse = issueAndDecodeToken.issueToken(newUser.id);
                UserAndToken userAndToken = new UserAndToken(tokenForNewUse, newUser);

                return userAndToken;
            } else
                throw new UserException("Login already exists");//Response("Login already exists", Response.Status.smthWrong);
        } else
            throw new UserException("Email already exists");//Response("Email already exists", Response.Status.smthWrong);
    }

    //проверка уникальности емаила
    public boolean checkEmailOfUser(String email) {
        Query queryForEmail = getSession()
                .createQuery("from User u where u.email=:email")
                .setParameter("email", email);
        List<User> userListForEmail = queryForEmail.list();
        if (userListForEmail.isEmpty())
            return true;
        else
            return false;
    }

    public boolean checkLoginOfUser(String login) {
        Query queryForLogin = getSession()
                .createQuery("from User u where u.login=:login")
                .setParameter("login", login);
        List<User> userListForLogin = queryForLogin.list();
        if (userListForLogin.isEmpty())
            return true;
        else
            return false;
    }

    // for second request in Controller for authorization
    public UserAndToken identifyUserForAutorization(String login, String password) {
        Query query = getSession()
                .createQuery("from User u where u.login=:login")
                .setParameter("login", login);
        List<User> list = query.list();
        try {
            User userFromDB = list.get(0);
            if (userFromDB.password.equals(password)) {
                String token = issueAndDecodeToken.issueToken(userFromDB.id);
                return new UserAndToken(token, userFromDB);
            } else throw new UserException("Login or Password is incorrect");
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    public boolean identifyUserByToken(String token, int userId) {
        try {
            ForDecode idAndDates = issueAndDecodeToken.decode(token);
            return idAndDates.dateExpire.after(new GregorianCalendar().getTime())
                    && idAndDates.userId == userId;
        } catch (Exception e) {
            return false;
        }
    }
}
