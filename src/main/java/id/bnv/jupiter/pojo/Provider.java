package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "provider")
public class Provider extends Object implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Provider_id")
    public int providerId;
    @Column(name = "Provider_name")
    public String providerName;

    public Provider() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId == provider.providerId &&
                Objects.equals(providerName, provider.providerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, providerName);
    }
}
