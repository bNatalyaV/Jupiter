package id.bnv.jupiter.authentication;

import id.bnv.jupiter.pojo.User;
import id.bnv.jupiter.pojo.UserAndToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authentication")
public class RegisterAndAuthorizationController {
    private final Authentication authentication;

    @Autowired
    public RegisterAndAuthorizationController(Authentication authentication) {
        this.authentication = authentication;
    }

    @PostMapping(value = "/new")
    public ResponseEntity register(@RequestBody User user) {
        UserAndToken response = authentication.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity authenticateUser(@RequestBody User user) {
        UserAndToken response = authentication.identifyUserForAuthorization(user.login, user.password);
        return ResponseEntity.ok(response);
    }
}
