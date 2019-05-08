package id.bnv.jupiter.pojo;

import java.io.Serializable;

public class UserAndToken implements Serializable {
    private final static long serialVersionUID=1L;

    public String token;
    public User user;

    public UserAndToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
