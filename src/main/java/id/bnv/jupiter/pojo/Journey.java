package id.bnv.jupiter.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "journey")
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Journey_id")
    public int journeyId;
    @Column(name = "Phone_number_id")
    public int phoneNumberId;
    @Column(name = "Tariff_id")
    public int tarifId;
    //    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Start_date")
    public Date startDate;
    //    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "End_date")
    public Date endDate;
    @Column(name = "Old_tariff_id")
    public int oldTariffId;

    public Journey() {
    }

    public Journey(Date startDate, int phoneNumberId, int oldTariffId, int tarifId) {
        this.startDate = startDate;
        this.phoneNumberId = phoneNumberId;
        this.oldTariffId = oldTariffId;
        this.tarifId = tarifId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journey journey = (Journey) o;
        return journeyId == journey.journeyId &&
                Objects.equals(journeyId, journey.journeyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journeyId);
    }
}
