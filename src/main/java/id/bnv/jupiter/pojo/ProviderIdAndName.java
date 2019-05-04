package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProviderIdAndName implements Serializable {
    private final static long serialVersionUID=1L;

    public Set<Integer> listProviderId;
    public Set<String> listProviderName;


    public ProviderIdAndName(Set<Integer> listProviderId, Set<String> listProviderName) {
        this.listProviderId = listProviderId;
        this.listProviderName = listProviderName;
    }

    public ProviderIdAndName() {
    }
}
