package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.Task;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TaskDao extends Dao {

    @Autowired
    public TaskDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Task getTask(int id) {
        Task task = getSession().get(Task.class, id);
        return task;
    }
}
