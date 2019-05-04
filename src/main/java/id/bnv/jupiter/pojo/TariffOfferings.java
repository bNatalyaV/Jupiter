package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class TariffOfferings implements Serializable {
    private final static long serialVersionUID = 1L;

    public int tariffId;
    public int offeringId;
    public String offeringPrice;
    public String quantity;
    public String offeringName;
    public String offeringDesc;


    public TariffOfferings() {
    }

    public TariffOfferings(int tariffId, int offeringId, String offeringPrice,
                           String quantity, String offeringName, String offeringDesc) {
        this.tariffId = tariffId;
        this.offeringId = offeringId;
        this.offeringPrice = offeringPrice;
        this.quantity = quantity;
        this.offeringName = offeringName;
        this.offeringDesc = offeringDesc;
    }
}
