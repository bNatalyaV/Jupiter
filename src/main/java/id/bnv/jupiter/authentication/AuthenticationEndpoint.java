package id.bnv.jupiter.authentication;

import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;


//выдача пользователю токена
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationEndpoint {

    private final UserDao dao;

    @Autowired
    public AuthenticationEndpoint(UserDao dao) {
        this.dao = dao;
    }

    @PostMapping
    public ResponseEntity authenticateUser(@RequestParam("username") String email,
                                           @RequestParam("password") String password) {
        try {
            if (authenticate(email, password)) {
                String token = "";//issueToken(email); // Issue a token for the user
                return ResponseEntity.ok(token);// Return the token on the response
            } else {
                return null;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private boolean authenticate(String email, String password) throws Exception {
        User user = dao.getUser(email);
        String passwordFromDB = user.email;
        return (passwordFromDB.equals(password)) ? true : false;
    }

//    private String issueToken(String email) {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.RS256);
//
//        String jwt = Jwts.builder()
//                .setSubject(email)
//                .setPayload(email)
//                .signWith(key)
//                .compact();
//        return jwt;
//    }
}
