package id.bnv.jupiter.authentication;

import com.auth0.jwt.algorithms.Algorithm;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationEndpoint {
    static Algorithm algorithm = Algorithm.HMAC256("jupiter");

    private final UserDao userDao;

    @Autowired
    public AuthenticationEndpoint(UserDao dao) {
        this.userDao = dao;
    }

    @PostMapping(value = "/new")
    public ResponseEntity register(@RequestBody User user) {
        Object response = userDao.registerUser(user);
        if (response.equals("Email already existed"))
            return ResponseEntity.badRequest().body("Email already existed");
        else if (response.equals("Login already existed"))
            return ResponseEntity.badRequest().body("Login already existed");
        else return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity authenticateUser(@RequestParam("login") String login,
//                                           @RequestParam("password") String password) {
//        try {
//            if (authenticate(email, password)) {
//                String token = issueToken(email);// Issue a token for the user
//
//                return ResponseEntity.ok(token);// Return the token on the response
//
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//
//            return ResponseEntity.badRequest().body("Error");
//
//        }
//    }

    private boolean authenticate(String login, String password) throws Exception {
        User user = userDao.getUserBy(login);
        String passwordFromDB = user.password;
        return (passwordFromDB.equals(password)) ? true : false;
    }
}
