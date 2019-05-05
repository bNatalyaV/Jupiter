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

    @GetMapping(value = "/numbers")
    public ResponseEntity getAllNumbers(User user) {
        List<PhoneNumber> phoneNumbers = dao.getAllNumbersOfUser(user);
        return ResponseEntity.ok(phoneNumbers);
    }

    //передача номера строкой, создать номер в бд
    @PutMapping(value = "/addnumber/{iduser}")
    public ResponseEntity addNewNumber(@RequestBody PhoneNumber number) {
        dao.addNumber(number);
        return ResponseEntity.ok(number);
    }

    @DeleteMapping(value = "/number")
    public ResponseEntity deleteNumber(User user, PhoneNumber number) {
        dao.deleteNumber(user, number);
        return ResponseEntity.ok(number);
    }

    @GetMapping(value = "/info/{idnumber}")
    public ResponseEntity getInfoAboutNumber(@PathVariable(value = "idnumber") int numberId) {
        InfoAboutNumber infoAboutNumber = dao.getInfoAboutNumberByNumberId(numberId);
        return ResponseEntity.ok(infoAboutNumber);
    }

    @GetMapping(value = "/fullinfo/{iduser}")
    public ResponseEntity getFullInformationAboutNumber(@PathVariable(value = "iduser") int userId) {
        List<FullInfoAboutNumber> list = dao.getFullInfoAboutNumber(userId);
        return ResponseEntity.ok(list);
    }
}