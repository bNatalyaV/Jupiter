package id.bnv.jupiter.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    public int id;
    @Column(name = "Surname")
    public String surname;
    @Column(name = "Name")
    public String name;
    @Column(name = "Patronymic")
    public String patronymic;
    @Column(name = "Passport")
    public String passport;
    @Column(name = "Email")
    public String email;
    @Column(name = "Password")
    public String password;
    @Column(name = "Birth_date")
    public Date birthDate;

    public User() {

    }
}
