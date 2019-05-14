package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.List;

public class JourneyAndTasks implements Serializable {
    private static final long serialVersionUID=1L;

    public FullInfoAboutTarif fullInfoAboutTarif;
    public List<JourneyTask> journeyTaskList;

    public JourneyAndTasks() {
    }

    public JourneyAndTasks(FullInfoAboutTarif fullInfoAboutTarif, List<JourneyTask> journeyTaskList) {
        this.fullInfoAboutTarif = fullInfoAboutTarif;
        this.journeyTaskList = journeyTaskList;
    }
}
