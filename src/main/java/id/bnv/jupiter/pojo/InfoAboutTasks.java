package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class InfoAboutTasks implements Serializable {
    private static final long serialVersionUID=1L;

    public String taskName;
    public JourneyTask journeyTask;

    public InfoAboutTasks() {
    }

    public InfoAboutTasks(String taskName, JourneyTask journeyTask) {
        this.taskName = taskName;
        this.journeyTask = journeyTask;
    }
}
