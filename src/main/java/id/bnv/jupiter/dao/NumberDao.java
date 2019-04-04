package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Number;
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
public class NumberDao extends Dao {

    @Autowired
    public NumberDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Number> getAllNumbersOfUser(User user) {
        Query query = getSession().createQuery("from Number u where u.userId=:userId");
        query.setParameter("userId", user.id);
        List<Number> numbers = query.list();
        return numbers;
    }
}