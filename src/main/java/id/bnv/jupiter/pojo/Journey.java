package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @Column(name = "Start_date")
    public Date startDate;
    @Column(name = "End_date")
    public Date endDate;

    public Journey() {
    }
}
