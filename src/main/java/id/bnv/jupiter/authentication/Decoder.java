package id.bnv.jupiter.authentication;

import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static id.bnv.jupiter.authentication.AuthenticationEndpoint.decode;

@Repository
@Transactional
public  class Decoder /* extends Dao */ {
    private final SessionFactory sessionFactoryForDecoder;

    @Autowired
    public Decoder(SessionFactory sessionFactory) {
        this.sessionFactoryForDecoder = sessionFactory;
    }

    Session getSession() {return sessionFactoryF}

    //
//    @Autowired
//    public Decoder(SessionFactory sessionFactory) {
//        super(sessionFactory);
//    }

    public String authorization(String token) {
        String emailFromToken = decode(token);
        Query query = getSession().createQuery("from User u where u.email=:email");
        query.setParameter("email", emailFromToken);
        List<User> list = query.list();
        if (list.isEmpty()) return null;
        else return list.get(0).email;
    }

}
