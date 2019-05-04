package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.AdditionalOfferingDao;
import id.bnv.jupiter.pojo.AdditionalOffering;
import id.bnv.jupiter.pojo.TariffAndOfferings;
import id.bnv.jupiter.pojo.TariffOfferings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/offer")
public class AdditionalOfferingController {
    private final AdditionalOfferingDao dao;

    @Autowired
    public AdditionalOfferingController(AdditionalOfferingDao dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/offers")
    public ResponseEntity getAllOfferings() {
        List<AdditionalOffering> offers = dao.getAllOffering();
        return ResponseEntity.ok(offers);
    }

    @GetMapping(value = "/offerings/{regionId}/{providerId}")
    public ResponseEntity getAllInfoAboutAdditionalOfferings(@PathVariable int regionId, @PathVariable int providerId) {
        List<TariffAndOfferings> list = dao.getAdditionalOfferings(regionId, providerId);
        return ResponseEntity.ok(list);
    }
}
