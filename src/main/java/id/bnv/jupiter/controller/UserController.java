package id.bnv.jupiter.controller;

import id.bnv.jupiter.authentication.Authentication;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserDao dao;
    private final Authentication authentication;

    @Autowired
    public UserController(UserDao dao, Authentication authentication) {
        this.dao = dao;
        this.authentication=authentication;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity getUserById(@PathVariable int id,
                                      @RequestHeader(value = "token") String token,
                                      @RequestHeader(value = "userid") String userId) {
        if (authentication.identifyUserByToken(token, Integer.parseInt(userId))) {
            User user = dao.getUser(id);


            if (user == null) {
                return ResponseEntity.badRequest().body("User not exist");
            }

            return ResponseEntity.ok(user);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/users")
    public ResponseEntity getAllUsers(
            @RequestHeader(value = "token") String token,
            @RequestHeader(value = "userid") String userId) {
        List<User> users = dao.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/user")
    public ResponseEntity createUser(@RequestBody User user,
                                     @RequestHeader(value = "token") String token,
                                     @RequestHeader(value = "userid") String userId) {
        dao.create(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/user")
    public ResponseEntity updateUser(@RequestBody User user,
                                     @RequestHeader(value = "token") String token,
                                     @RequestHeader(value = "userid") String userId) {
        dao.update(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity deleteUser(@PathVariable int id,
                                     @RequestHeader(value = "token") String token,
                                     @RequestHeader(value = "userid") String userId) {
        User user = dao.getUser(id);
        dao.delete(user);

        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/user/enter")
//    public ResponseEntity login(User user) {
//        String email = user.email;
//        String password = user.password;
//
//        User user1 = dao.getUser(email);
//
//        if (user1 == null) {
//            return ResponseEntity.badRequest().body("no user with this email exist");
//        }
//
//        if (user1.password.equals(password)) {
//            return ResponseEntity.ok(user1);
//        }
//
//        return ResponseEntity.status(401).body("password is wrong");
//    }
}
