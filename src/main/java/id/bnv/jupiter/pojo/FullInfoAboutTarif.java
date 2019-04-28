package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.Date;

public class FullInfoAboutTarif implements Serializable {
    private static final long serialVersionUID = 1L;

    public String previousProvider;
    public String previousTarif;
    public String number;
    public Date date;
    public String nextProvider;
    public String nextTarif;

    public FullInfoAboutTarif() {
    }

    public FullInfoAboutTarif(String previousProvider, String previousTarif, String number,
                              Date date, String nextProvider, String nextTarif) {
        this.previousProvider = previousProvider;
        this.previousTarif = previousTarif;
        this.number = number;
        this.date = date;
        this.nextProvider = nextProvider;
        this.nextTarif = nextTarif;
    }
}
