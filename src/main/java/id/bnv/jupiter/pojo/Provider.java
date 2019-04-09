package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tariff")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int providerId;
    @Column(name = "Provider_name")
    public String providerName;

    public Provider() {
    }
}
