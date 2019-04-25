package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "task")
public class Task implements Serializable {
    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Task_id")
    public int taskId;
    @Column(name = "Task_desc")
    public String taskDesc;
    @Column(name = "Task_request")
    public String taskRequest;
    @Column(name = "Task_name")
    public String taskName;


    public Task() {
    }
}
