package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "adress")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Adress_id")
    public int adressId;
    @Column(name = "City_id")
    public int cityId;
    @Column(name = "Street")
    public String street;
    @Column(name = "Building")
    public String building;


    public Address() {
    }
}
