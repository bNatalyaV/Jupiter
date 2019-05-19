package id.bnv.jupiter.authentication;

import id.bnv.jupiter.exception.UserException;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.ForDecode;
import id.bnv.jupiter.pojo.User;
import id.bnv.jupiter.pojo.UserAndToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

@Component
public class Authentication {
    private final IssueAndDecodeToken issueAndDecodeToken;
    private final UserDao userDao;

    @Autowired
    public Authentication(IssueAndDecodeToken iADT, UserDao userDao) {
        this.issueAndDecodeToken = iADT;
        this.userDao = userDao;
    }

    UserAndToken registerUser(User user) {
        if (userDao.checkEmailExist(user.email, 0)) throw new UserException("email exist");
        if (userDao.checkLoginExist(user.login, 0)) throw new UserException("login exist");

        User newUser = new User(user.email, user.login, user.password);

        userDao.create(newUser);

        String tokenForNewUse = issueAndDecodeToken.issueToken(newUser.id);
        return new UserAndToken(tokenForNewUse, newUser);
    }

    UserAndToken identifyUserForAuthorization(String login, String password) {
        User user = userDao.getUserByLogin(login);

        if (user == null) throw new UserException("Login not exist");

        if (user.password.equals(password)) {
            String token = issueAndDecodeToken.issueToken(user.id);
            return new UserAndToken(token, user);
        }

        throw new UserException("Login or Password is incorrect");
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
