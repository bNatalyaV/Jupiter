package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class OffersAndAddOffers implements Serializable {
    private final static long serialVersionUID = 1L;

    public int offeringId;
    public String offeringPrice;
    public String quantity;
    public String offeringName;
    public String offeringDesc;

    public OffersAndAddOffers() {
    }

    public OffersAndAddOffers(int offeringId, String offeringPrice,
                              String quantity, String offeringName, String offeringDesc) {
        this.offeringId = offeringId;
        this.offeringPrice = offeringPrice;
        this.quantity = quantity;
        this.offeringName = offeringName;
        this.offeringDesc = offeringDesc;
    }

    public OffersAndAddOffers(String offeringPrice, String quantity, String offeringName) {
        this.offeringPrice = offeringPrice;
        this.quantity = quantity;
        this.offeringName = offeringName;
    }
}
