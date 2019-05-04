package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.List;

public class TariffAndOfferings implements Serializable {
    private final static long serialVersionUID=1L;

    public int tariffId;
    public String tariffName;
    public String tariffPrice;
    public List<TariffOfferings> tarifOfferings;


    public TariffAndOfferings(int tariffId, String tariffName, String tariffPrice) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
    }

    public TariffAndOfferings(int tariffId, String tariffName, String tariffPrice, List<TariffOfferings> tarifOfferings) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
        this.tarifOfferings = tarifOfferings;
    }

    public void setTarifOfferings(List<TariffOfferings> list) {
        tarifOfferings=list;
    }
}
