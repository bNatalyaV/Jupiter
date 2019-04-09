package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tariff")
public class JourneyTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
