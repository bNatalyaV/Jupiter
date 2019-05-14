package id.bnv.jupiter.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class FullInfoAboutTarif implements Serializable {
    private static final long serialVersionUID = 1L;

    public String previousProvider;
    public String previousTarif;
    public String number;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date date;
    public String nextProvider;
    public String nextTarif;
    public int journeyId;
    public int numberId;
    public int newTariffId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date endDate;

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

    public FullInfoAboutTarif(String previousProvider, String previousTarif, String number,
                              Date date, String nextProvider, String nextTarif, int journeyId,
                              int numberId, int newTariffId, Date endDate) {
        this.previousProvider = previousProvider;
        this.previousTarif = previousTarif;
        this.number = number;
        this.date = date;
        this.nextProvider = nextProvider;
        this.nextTarif = nextTarif;
        this.journeyId = journeyId;
        this.numberId = numberId;
        this.newTariffId = newTariffId;
        this.endDate = endDate;
    }
}
