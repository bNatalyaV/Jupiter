package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.NumberDao;
import id.bnv.jupiter.dao.TarifDao;
import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.Tarif;
import id.bnv.jupiter.pojo.TarifInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static id.bnv.jupiter.authentication.Decoder.auth;

@RestController
@RequestMapping(value = "/tarif")
public class TarifController {
    private final TarifDao dao;
    private final NumberDao numberDao;

    @Autowired
    public TarifController(TarifDao dao, NumberDao numberdao) {
        this.dao = dao;
        this.numberDao = numberdao;
    }

    @GetMapping(value = "/")
    public ResponseEntity getTarifByNumber(@RequestHeader String token, PhoneNumber number) {
        if (auth(token)) {
            Tarif tarif = dao.getTarifByNumber(number);
            return ResponseEntity.ok(tarif);
        } else return ResponseEntity.badRequest().body("Not authorized");
    }

    @GetMapping(value = "/info/{tarifId}")
    public ResponseEntity getInfo(@PathVariable int tarifId) {
        TarifInfo tarifInfo = dao.getInfo(tarifId);
        return ResponseEntity.ok(tarifInfo);
    }

    @PostMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity addOrUpdateTarif(@PathVariable(value = "idnumber") int idNumber,
                                   @PathVariable(value = "idtarif") int idTarif) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber=numberDao.getNumberById(idNumber);

        if (dao.hasTariff(phoneNumber)) dao.changeTariff(idNumber, idTarif);
        else dao.addTarifToNumber(idNumber, idTarif);
        return ResponseEntity.ok().build();
    }

        //        PhoneNumber phouneNumber = new PhoneNumber();
//        dao.addTarifForNumber(idNumber, idTarif);
//        if (phoneNumber.hasTarif) {
//            return changeTarif(phoneNumber, idTarif);
//        }
//        // save
//        return ResponseEntity.ok("Successfully!");
//    }
    @PutMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity changeTarif(@PathVariable int idnumber, @PathVariable int idtarif) {
        dao.changeTariff(idnumber, idtarif);
        return ResponseEntity.ok().build();
    }
}
