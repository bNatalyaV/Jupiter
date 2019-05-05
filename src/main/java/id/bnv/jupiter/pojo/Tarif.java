package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tariff")
public class Tarif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tariff_id")
    public int tarifId;
    @Column(name = "Region_id")
    public int regionId;
    @OneToOne(targetEntity = TarifInfo.class)
    @JoinColumn(name = "Tariff_inf_id", referencedColumnName = "Tariff_inf_id")
    public TarifInfo tarifInfoId;
    @Column(name = "Tariff_price")
    public String tarifPrice;

    public Tarif() {
    }
}
