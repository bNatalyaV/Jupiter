package id.bnv.jupiter.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static id.bnv.jupiter.authentication.IssueTokenAndDecode.issueToken;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationEndpoint {
    static Algorithm algorithm = Algorithm.HMAC256("jupiter");

    private final UserDao dao;

    @Autowired
    public AuthenticationEndpoint(UserDao dao) {
        this.dao = dao;
    }

    @PostMapping
    public ResponseEntity register(@RequestParam("email") String email,
                                   @RequestParam("login") String login,
                                   @RequestParam("password") String password) {

    }

    @PostMapping
    public ResponseEntity authenticateUser(@RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        try {
            if (authenticate(email, password)) {
                String token = issueToken(email);// Issue a token for the user
                return ResponseEntity.ok(token);// Return the token on the response
            } else {
                return null;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private boolean authenticate(String login, String password) throws Exception {
        User user = dao.getUserBy(login);
        String passwordFromDB = user.password;
        return (passwordFromDB.equals(password)) ? true : false;
    }
}
