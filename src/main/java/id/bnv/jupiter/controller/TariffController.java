package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.NumberDao;
import id.bnv.jupiter.dao.TarifDao;
import id.bnv.jupiter.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tarif")
public class TariffController {
    private final TarifDao dao;
    private final NumberDao numberDao;

    @Autowired
    public TariffController(TarifDao dao, NumberDao numberdao) {
        this.dao = dao;
        this.numberDao = numberdao;
    }

    @GetMapping(value = "/tariffname/{idprovider}")
    public ResponseEntity getTariffNameByProviderId(@PathVariable(value = "idprovider") int providerId,
                                                    @RequestHeader(value = "token") String token,
                                                    @RequestHeader(value = "userid") String userId) {
        TarifInfo tarifInfo = dao.getTarifInfoByProviderId(providerId);
        return ResponseEntity.ok(tarifInfo.tarifName);
    }

    @GetMapping(value = "/tariff/{idprovider}")
    public ResponseEntity getTarifByProviderId(@PathVariable(value = "idprovider") int providerId,
                                               @RequestHeader(value = "token") String token,
                                               @RequestHeader(value = "userid") String userId) {
        TarifInfo tarifInfo = dao.getTarifInfoByProviderId(providerId);
        Tarif tarif = dao.getTarifByTarifInfoId(tarifInfo.tarifInfoId);
        return ResponseEntity.ok(tarif);
    }

    @GetMapping(value = "/tariffoffering/{idoffering}")
    public ResponseEntity getTarifOfferingByOfferingId(@PathVariable(value = "idoffering") int offeringId,
                                                       @RequestHeader(value = "token") String token,
                                                       @RequestHeader(value = "userid") String userId) {
        TarifOffering tarifOffering = dao.getTarifOffering(offeringId);
        return ResponseEntity.ok(tarifOffering);
    }

    @GetMapping(value = "info/{idnumber}/{idnexttarif}")
    public ResponseEntity getFullInfoAboutTarif(@PathVariable(value = "idnumber") int numberId,
                                                @PathVariable(value = "idnexttarif") int nextTarifId,
                                                @RequestHeader(value = "token") String token,
                                                @RequestHeader(value = "userid") String userId) {
        FullInfoAboutTarif infoAboutTarif = dao.getFullInfoAboutTarif(numberId, nextTarifId);
        return ResponseEntity.ok(infoAboutTarif);
    }

    @PostMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity addOrUpdateTarif(@PathVariable(value = "idnumber") int idNumber,
                                           @PathVariable(value = "idtarif") int idTarif,
                                           @RequestHeader(value = "token") String token,
                                           @RequestHeader(value = "userid") String userId) {
        PhoneNumber phoneNumber = numberDao.getNumberById(idNumber);
        if (dao.hasTariff(phoneNumber)) {
            changeTarif(phoneNumber.id, idTarif, token, userId);
        } else dao.addTarifToNumber(idNumber, idTarif);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/tarif/{idnumber}/{idtarif}")
    public ResponseEntity changeTarif(@PathVariable int idnumber, @PathVariable int idtarif,
                                      @RequestHeader(value = "token") String token,
                                      @RequestHeader(value = "userid") String userId) {
        dao.changeTariff(idnumber, idtarif);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/price/{regionId}/{providerId}")
    public ResponseEntity getTariffNameIdPrice(@PathVariable int regionId, @PathVariable int providerId) {
        List<TariffNameIdPrice> list = dao.getTariffNameIdPrice(regionId, providerId);
        return ResponseEntity.ok(list);
    }
}
