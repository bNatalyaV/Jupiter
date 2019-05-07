package id.bnv.jupiter.authentication;

import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.pojo.ForDecode;
import id.bnv.jupiter.pojo.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public User identifyUser(String token) {
        ForDecode emailFromToken = issueAndDecodeToken.decode(token);
        Query query = getSession().createQuery("from User u where u.email=:email");
        query.setParameter("email", emailFromToken);
        List<User> list = query.list();
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    public boolean auth(String token) {
        User user = identifyUser(token);
        return (!user.equals(null)) ? true : false;
    }
}
