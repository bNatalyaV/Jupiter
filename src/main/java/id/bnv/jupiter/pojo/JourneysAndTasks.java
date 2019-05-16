package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.List;

public class JourneysAndTasks implements Serializable {
    private final static long serialVersionUID=1L;

    public FullInfoAboutTarif fullInfoAboutTarif;
    public List<InfoAboutTasks> infoAboutTasksList;

    public JourneysAndTasks() {
    }

    public JourneysAndTasks(FullInfoAboutTarif fullInfoAboutTarif,
                            List<InfoAboutTasks> infoAboutTasksList) {
        this.fullInfoAboutTarif = fullInfoAboutTarif;
        this.infoAboutTasksList = infoAboutTasksList;
    }
}
