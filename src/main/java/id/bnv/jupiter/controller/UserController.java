package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.Dao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private final Dao dao;

    @Autowired
    public UserController(Dao dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        User user = dao.getUser(id);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not exist");
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/user")
    public ResponseEntity getUserByIdRequestParam(@RequestParam(value = "id") int id) {
        User user = dao.getUser(id);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not exist");
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/users")
    public ResponseEntity getAllUsers() {
        List<User> users = dao.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/user")
    public ResponseEntity createUser(@RequestBody User user) {
        dao.create(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/user")
    public ResponseEntity updateUser(@RequestBody User user) {
        dao.update(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User user = dao.getUser(id);
        dao.delete(user);

        return ResponseEntity.ok().build();
    }
}