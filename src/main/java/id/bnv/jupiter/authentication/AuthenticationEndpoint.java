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
                String token = issueToken(email); // Issue a token for the user
                return ResponseEntity.ok(token);// Return the token on the response
            } else {
                return null;
            }
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private boolean authenticate(String email, String password) throws Exception {
        User user = dao.getUser(email);
        String passwordFromDB = user.email;
        return (passwordFromDB.equals(password)) ? true : false;


        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    }

}
