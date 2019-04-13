package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "Additional_offering")
public class AdditionalOffering implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Offering_id")
    public int offeringId;
    @Column(name = "Offering_name")
    public int offeringName;
    @Column(name = "Offering_desc")
    public String offeringDesc;

    public AdditionalOffering() {
    }
}
