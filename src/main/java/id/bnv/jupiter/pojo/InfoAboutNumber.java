package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class InfoAboutNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    public String number;
    public String providerName;
    public String tarifName;


    public InfoAboutNumber() {
    }

    public InfoAboutNumber(String number, String providerName, String tarifName) {
        this.number = number;
        this.providerName = providerName;
        this.tarifName = tarifName;
    }
}
