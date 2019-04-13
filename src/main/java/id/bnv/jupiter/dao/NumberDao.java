package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class NumberDao extends Dao {

    @Autowired
    public NumberDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<PhoneNumber> getAllNumbersOfUser(User user) {
        Query query = getSession().createQuery("from PhoneNumber u where u.userId=:userId");
        query.setParameter("userId", user.id);
        List<PhoneNumber> phoneNumbers = query.list();
        return phoneNumbers;
    }

    public PhoneNumber addNumber(User user, PhoneNumber number) {
        number.userId = user.id;
        create(number);
        return number;
    }

    public PhoneNumber deleteNumber(User user, PhoneNumber number) {
        number.userId = user.id;
        delete(number);
        return number;
    }
}