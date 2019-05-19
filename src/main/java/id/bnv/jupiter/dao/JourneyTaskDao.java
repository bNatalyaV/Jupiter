package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.JourneyTask;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JourneyTaskDao extends Dao {

    @Autowired
    public JourneyTaskDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public JourneyTask getJourneyTask(int id) {
        return getSession().get(JourneyTask.class, id);
    }
}
