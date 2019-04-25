package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Journey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JourneyDao extends Dao {

    @Autowired
    public JourneyDao(SessionFactory sessionFactory) {super(sessionFactory);}

    public Journey getJourney(int idJourney) {
        Session session= getSession();
        Journey journey=session.get(Journey.class, idJourney);
        return journey;
    }
}
