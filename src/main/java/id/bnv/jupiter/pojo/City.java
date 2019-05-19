package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "city")
public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "City_id")
    public int cityId;
    @Column(name = "Region_id")
    public int regionId;
    @Column(name = "City_name")
    public String cityName;

    public City() {
    }
}
