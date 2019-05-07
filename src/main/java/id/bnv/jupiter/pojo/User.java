package id.bnv.jupiter.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    public int id;
    @Column(name = "login")
    public String login;
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
    //@Temporal(TemporalType.DATE)// if need ms
    @JsonFormat(pattern = "yyyy-MM-dd") //
    public Date birthDate;

    public User() {

    }

    public User(String email, String login, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
}

