package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.TarifDao;
import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.Tarif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tarif")
public class TarifController {
    private final TarifDao dao;

    @Autowired
    public TarifController(TarifDao dao) {
        this.dao = dao;
    }

    @GetMapping(name = "/")
    public ResponseEntity getTarifByNumber(PhoneNumber number) {
        Tarif tarif = dao.getTarifByNumber(number);
        return ResponseEntity.ok(tarif);
    }
    @PostMapping(name = "/newtarif")
    public ResponseEntity addTarif(PhoneNumber number, Tarif tarif) {
        dao.addTarifForNumber(number, tarif);
        return ResponseEntity.ok("Successfully!");
    }
    @PutMapping(value = "/changetarif")
    public ResponseEntity changeTarif(PhoneNumber number, Tarif tarif) {
        dao.changeTariff(number, tarif);
        return ResponseEntity.ok(tarif);
    }
}
