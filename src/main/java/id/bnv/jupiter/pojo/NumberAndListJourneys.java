package id.bnv.jupiter.pojo;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.List;

public class NumberAndListJourneys implements Serializable {
    private final static long serialVersionUID=1L;

    public int numberId;
    public List<JourneyAndTasks> journeyAndTasksList;

    public NumberAndListJourneys() {
    }

    public NumberAndListJourneys(int numberId, List<JourneyAndTasks> journeyAndTasksList) {
        this.numberId = numberId;
        this.journeyAndTasksList = journeyAndTasksList;
    }
}
