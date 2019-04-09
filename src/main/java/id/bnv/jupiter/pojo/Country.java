package id.bnv.jupiter.pojo;

import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Country_id")
    public int countryId;
    @Column(name = "Country_name")
    public String countryName;

    public Country() {
    }
}
