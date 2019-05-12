package id.bnv.jupiter.authentication;

import id.bnv.jupiter.pojo.User;
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
        Object response = authentication.registerUser(user);
        if (response.equals("Email already existed"))
            return ResponseEntity.badRequest().body("Email already existed");
        else if (response.equals("Login already existed"))
            return ResponseEntity.badRequest().body("Login already existed");
        else return ResponseEntity.ok(response);
    }

    // авторизация пользователя, вернуть токен+пользователь //done
    @PutMapping(value = "/signin")
    public ResponseEntity authenticateUser(@RequestBody User user) {
        try {
            Object response = authentication.identifyUserForAutorization(user.login, user.password);
            if (response.equals("Login or Password is incorrect"))

                return ResponseEntity.badRequest().body("Login or Password is incorrect");

            else return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Error");

        }
    }
}
