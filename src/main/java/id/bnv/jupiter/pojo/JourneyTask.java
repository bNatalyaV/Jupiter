package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "journey_task")
public class JourneyTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Journey_id")
    public int journeyTaskId;
    @Column(name = "Task_id")
    public int taskId;
    @Column(name = "Task_start")
    public Date taskstart;
    @Column(name = "Task_finish")
    public Date taskfinish;

    public JourneyTask() {
    }
}
