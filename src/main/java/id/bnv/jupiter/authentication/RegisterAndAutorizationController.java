package id.bnv.jupiter.authentication;

import id.bnv.jupiter.pojo.User;
import id.bnv.jupiter.pojo.UserAndToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authentication")
public class RegisterAndAutorizationController {
    private final Authentication authentication;

    @Autowired
    public RegisterAndAutorizationController(Authentication authentication) {
        this.authentication = authentication;
    }

    @PostMapping(value = "/new")
    public ResponseEntity register(@RequestBody User user) {
//        try {
            UserAndToken response = authentication.registerUser(user);
            return ResponseEntity.ok(response);
//        } catch (UserException userExeptions) {
//            return ResponseEntity.badRequest().body(userExeptions);
//        }
    }

    // авторизация пользователя, вернуть токен+пользователь //done
    @PutMapping(value = "/signin")
    public ResponseEntity authenticateUser(@RequestBody User user) {
//        try {
            UserAndToken response = authentication.identifyUserForAutorization(user.login, user.password);

            return ResponseEntity.ok(response);
//        } catch (UserException e) {
//
//            return ResponseEntity.badRequest().body(e);
//        }

    }
}
