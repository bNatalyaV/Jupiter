package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class TariffNameIdPrice implements Serializable {
    private final static long serialVersionUID=1L;

    public int tariffId;
    public String tariffName;
    public String tariffPrice;

    public TariffNameIdPrice() {
    }

    public TariffNameIdPrice(int tariffId, String tariffName, String tariffPrice) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
    }
}
