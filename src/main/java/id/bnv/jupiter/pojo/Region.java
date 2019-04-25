package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "region")
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Region_id")
    public int regionId;
    @Column(name = "Country_id")
    public int countryId;
    @Column(name = "Region_name")
    public String regionName;

    public Region() {

    }
}
