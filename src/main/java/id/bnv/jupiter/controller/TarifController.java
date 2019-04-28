package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.NumberDao;
import id.bnv.jupiter.dao.TarifDao;
import id.bnv.jupiter.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;

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

    @GetMapping(value = "/tariffname/{idprovider}")
    public ResponseEntity getTariffNameByProviderId(@PathVariable(value = "idprovider") int providerId) {
        TarifInfo tarifInfo = dao.getTarifInfoByProviderId(providerId);
        return ResponseEntity.ok(tarifInfo.tarifName);
    }
    @GetMapping(value = "/tariff/{idprovider}")
    public ResponseEntity getTarifByProviderId(@PathVariable (value = "idprovider" )int providerId) {
        TarifInfo tarifInfo=dao.getTarifInfoByProviderId(providerId);
        Tarif tarif=dao.getTarifByTarifInfoId(tarifInfo.tarifInfoId);
        return ResponseEntity.ok(tarif);
    }
    //6 request
    @GetMapping(value = "/tariffoffering/{idoffering}")
    public ResponseEntity getTarifOfferingByOfferingId(@PathVariable (value = "idoffering") int offeringId){
        TarifOffering tarifOffering=dao.getTarifOffering(offeringId);
        return ResponseEntity.ok(tarifOffering);
    }
    //vk request
    @GetMapping(value = "info/{idnumber}/{idnexttarif}")
    public ResponseEntity getFullInfoAboutTarif(@PathVariable(value = "idnumber") int numberId,
                                                @PathVariable(value = "idnexttarif") int nextTarifId){
        FullInfoAboutTarif infoAboutTarif=dao.getFullInfoAboutTarif(numberId, nextTarifId);
        return ResponseEntity.ok(infoAboutTarif);
    }

    @PostMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity addOrUpdateTarif(@PathVariable(value = "idnumber") int idNumber,
                                           @PathVariable(value = "idtarif") int idTarif) {
        PhoneNumber phoneNumber = numberDao.getNumberById(idNumber);
        if (dao.hasTariff(phoneNumber)) {
            changeTarif(phoneNumber.id, idTarif);
//            if (dao.changeTariff(idNumber, idTarif)==true) return ResponseEntity.ok("Tariff was changed");
//            else return ResponseEntity.badRequest().body("Balance is less than zero");
        } else dao.addTarifToNumber(idNumber, idTarif);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity changeTarif(@PathVariable int idnumber, @PathVariable int idtarif) {
        dao.changeTariff(idnumber, idtarif);
        return ResponseEntity.ok().build();
    }
}
