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

        if (response instanceof Response) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
//        if (response.equals("{\"Email already existed\"}"))
//            return ResponseEntity.badRequest().body("{\"response\" : \"Email already existed\"}");
//        else if (response.equals("{\"response\" : \"Login already existed\"}"))
//            return ResponseEntity.badRequest().body( "{\"response\" : \"Login already existed\"}");
//        else return ResponseEntity.ok(response);
    }

    // авторизация пользователя, вернуть токен+пользователь //done
    @PutMapping(value = "/signin")
    public ResponseEntity authenticateUser(@RequestBody User user) {
        try {
            Object response = authentication.identifyUserForAutorization(user.login, user.password);
            if (response instanceof Response)
                return ResponseEntity.badRequest().body(response);

            else return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();

        }
    }
}
