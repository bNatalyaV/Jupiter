package id.bnv.jupiter.controller;

import id.bnv.jupiter.exception.UserException;
import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserDao dao;

    @Autowired
    public UserController(UserDao dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity getUserById(@PathVariable int id,
                                      @RequestHeader(value = "token") String token,
                                      @RequestHeader(value = "userid") String userId) {
        User user = dao.getUser(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/user")
    public ResponseEntity createUser(@RequestBody User user,
                                     @RequestHeader(value = "token") String token,
                                     @RequestHeader(value = "userid") String userId) {
        dao.create(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/user")
    public ResponseEntity updateUser(@RequestBody User user){
                                    // @RequestHeader(value = "token") String token,
                                    // @RequestHeader(value = "userid") String userId) {
        boolean isEmailAlreadyExist = dao.checkEmailExist(user.email, user.id);
        if (isEmailAlreadyExist) throw new UserException("email already exist");

        boolean isLoginAlreadyExist = dao.checkLoginExist(user.login, user.id);
        if (isLoginAlreadyExist) throw new UserException("login already exist");

        boolean isPassportAlreadyExist = dao.checkPassportExist(user.passport, user.id);
        if (isPassportAlreadyExist) throw new UserException("passport already exist");

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
}