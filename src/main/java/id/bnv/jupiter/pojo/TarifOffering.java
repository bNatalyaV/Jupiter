package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tariff_offering")
public class TarifOffering implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Offering_id")
    public int offeringId;
    @Column(name = "Tariff_id")
    public int tarifId;
    @Column(name = "Offering_price")
    public String offeringPrise;
    @Column(name = "Quantity")
    public String quantity;

    public TarifOffering() {
    }
}
