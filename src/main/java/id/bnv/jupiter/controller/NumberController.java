package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.NumberDao;
import id.bnv.jupiter.pojo.FullInfoAboutNumber;
import id.bnv.jupiter.pojo.InfoAboutNumber;
import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//
@RestController
@RequestMapping(value = "/numbersinfo")
public class NumberController {
    private final NumberDao dao;

    @Autowired
    public NumberController(NumberDao dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/numbers/{userId}")
    public ResponseEntity getAllNumbers(@PathVariable int userId,
                                        @RequestHeader(value = "token") String token,
                                        @RequestHeader(value = "userid") String userid) {
        List<PhoneNumber> phoneNumbers = dao.getAllNumbersOfUser(userId);
        return ResponseEntity.ok(phoneNumbers);
    }

    //передача номера строкой, создать номер в бд
    @PutMapping(value = "/addnumber")
    public ResponseEntity addNewNumber(@RequestBody PhoneNumber number,
                                       @RequestHeader(value = "token") String token,
                                       @RequestHeader(value = "userid") String userId) {
        try {
            PhoneNumber newNumber =dao.addNumber(number);
            return ResponseEntity.ok(newNumber);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }

    }

    @DeleteMapping(value = "/number")
    public ResponseEntity deleteNumber(User user, PhoneNumber number,
                                       @RequestHeader(value = "token") String token,
                                       @RequestHeader(value = "userid") String userId) {
        dao.deleteNumber(user, number);
        return ResponseEntity.ok(number);
    }

    @GetMapping(value = "/info/{idnumber}")
    public ResponseEntity getInfoAboutNumber(@PathVariable(value = "idnumber") int numberId,
                                             @RequestHeader(value = "token") String token,
                                             @RequestHeader(value = "userid") String userId) {
        InfoAboutNumber infoAboutNumber = dao.getInfoAboutNumberByNumberId(numberId);
        return ResponseEntity.ok(infoAboutNumber);
    }

    @GetMapping(value = "/fullinfo/{iduser}")
    public ResponseEntity getFullInformationAboutNumber(@PathVariable(value = "iduser") int userId,
                                                        @RequestHeader(value = "token") String token,
                                                        @RequestHeader(value = "userid") String userid) {
        List<FullInfoAboutNumber> list = dao.getFullInfoAboutNumber(userId);
        return ResponseEntity.ok(list);
    }
}