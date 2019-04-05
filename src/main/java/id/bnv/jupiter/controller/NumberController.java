package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.NumberDao;
import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/numbersinfo")
public class NumberController {
    private final NumberDao dao;

    @Autowired
    public NumberController(NumberDao dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/numbers")
    public ResponseEntity getAllNumbers(User user) {
        List<PhoneNumber> phoneNumbers = dao.getAllNumbersOfUser(user);


                dao.addNumber()
        return ResponseEntity.ok(phoneNumbers);
    }
}