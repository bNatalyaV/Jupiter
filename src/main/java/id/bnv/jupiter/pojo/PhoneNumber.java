package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "number")
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Phone_number_id")
    public int id;
    @Column(name = "User_id")
    public int userId;
    @Column(name = "Phone_number")
    public String phoneNumber;

   // @ManyToOne(optional = true)
   // @JoinColumn(name = "Phone_number_id")
    @Column(name = "Tariff_id")
    public int tarifId;

    @Column(name = "Balance")
    public int balance;

    public PhoneNumber() {

    }
}
