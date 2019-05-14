package id.bnv.jupiter.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "journey_task")
public class JourneyTask implements Serializable {

    private static final long serialVersionUID = 1L;


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    public int id;
    @Id
    @Column(name = "Journey_id")
    public int journeyId;
    @Id
    @Column(name = "Task_id")
    public int taskId;
    @Column(name = "Task_start")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date taskstart;
    @Column(name = "Task_finish")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date taskfinish;

    public JourneyTask() {
    }
    public JourneyTask(int journeyId, int taskId, Date taskstart) {
        this.journeyId = journeyId;
        this.taskId=taskId;
        this.taskstart=taskstart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyTask that = (JourneyTask) o;
        return journeyId == that.journeyId &&
                taskId == that.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journeyId, taskId);
    }
}
