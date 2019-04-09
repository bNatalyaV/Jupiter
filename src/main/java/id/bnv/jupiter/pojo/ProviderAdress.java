package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "provider_adress")
public class ProviderAdress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Provider_id")
    public int providerId;
    @Column(name = "Adress_id")
    public int adressId;

    public ProviderAdress() {
    }
}
