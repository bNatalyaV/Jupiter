package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tariff_inf")
public class TarifInfo implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tariff_inf_id")
    public int tarifInfoId;
    @Column(name = "Tariff_name")
    public String tarifName;
    @Column(name = "Tariff_descr")
    public String tarifDescr;

    public TarifInfo() {

    }
}