package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tariff")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int countryId;
    @Column(name = "Country_name")
    public String countryName;

    public Country() {
    }
}
