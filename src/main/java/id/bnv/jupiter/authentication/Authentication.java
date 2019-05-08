package id.bnv.jupiter.authentication;

import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.pojo.ForDecode;
import id.bnv.jupiter.pojo.User;
import id.bnv.jupiter.pojo.UserAndToken;
import org.hibernate.Session;
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
    public Object registerUser(User user) {
        if (checkEmailOfUser(user.email)) {
            if (checkLoginOfUser(user.login)) {
                User newUser = new User(user.email, user.login, user.password);
                create(newUser);
                String tokenForNewUse=issueAndDecodeToken.issueToken(user.id);
                UserAndToken userAndToken=new UserAndToken(tokenForNewUse, newUser);

                return userAndToken;
            } else
                return "Login already existed";
        } else
            return "Email already existed";
    }
//проверка уникальности емаила
    public boolean checkEmailOfUser(String email) {
        Query queryForEmail = getSession()
                              .createQuery("from User u where u.email=:email")
                              .setParameter("email", email);
        List<User> userListForEmail = queryForEmail.list();
        if(userListForEmail.isEmpty())
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
    public Object identifyUserForAutorization(String login, String password) {
        Query query = getSession()
                .createQuery("from User u where u.login=:login")
                .setParameter("login", login);
        List<User> list = query.list();
        User userFromDB = list.get(0);
        if (userFromDB.password == password) {
            String token = issueAndDecodeToken.issueToken(userFromDB.id);
            UserAndToken userAndToken = new UserAndToken(token, userFromDB);
            return userAndToken;
        } else return "Login or Password is incorrect";
    }
//если true, запрос проходит в метод
// false - вернуть на страницу авторизации (2 api контролерра авторизации)
    public boolean identifyUserByToken(String token, int userId) {
        ForDecode idAndDates=issueAndDecodeToken.decode(token);
        if (idAndDates.dateExpire.after(new GregorianCalendar().getTime()) && idAndDates.userId==userId)
            return true;
        else return false;
    }

//    public boolean auth(String token) {
//        User user = identifyUser(token);
//        return (!user.equals(null)) ? true : false;
//    }
}
