package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class FullInfoAboutNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    public String number;
    public String country;
    public String region;
    public String provider;
    public String tariff;
    public String tarifPrice;
    public int tariffId;
    public int countryId;

    public FullInfoAboutNumber() {
    }

    public FullInfoAboutNumber(String number, String country, String region, String provider,
                               String tariff, String  tarifPrice, int tariffId, int countryId) {
        this.number = number;
        this.country = country;
        this.region = region;
        this.provider = provider;
        this.tariff = tariff;
        this.tarifPrice = tarifPrice;
        this.tariffId=tariffId;
        this.countryId=countryId;
    }
}
