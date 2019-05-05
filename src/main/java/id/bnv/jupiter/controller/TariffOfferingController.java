package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.TariffOfferingDao;
import id.bnv.jupiter.pojo.OffersAndAddOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tariffoffers")
public class TariffOfferingController {

    private final TariffOfferingDao tariffOfferingDao;

    @Autowired
    public TariffOfferingController(TariffOfferingDao tariffOfferingDao) {
        this.tariffOfferingDao=tariffOfferingDao;
    }

    // по тарифу список офферингов, offering price != null
    @GetMapping(value = "/{tariffId}")
    public ResponseEntity getOffers(@PathVariable int tariffId) {
        List<OffersAndAddOffers> list=tariffOfferingDao.getOffersAndAddOffers(tariffId);

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/offers/{tariffId}")
    public ResponseEntity getOfferNamePriceQuantity(@PathVariable int tariffId) {
        List<OffersAndAddOffers> list=tariffOfferingDao.getOfferNamePriceQuantity(tariffId);

        return ResponseEntity.ok(list);
    }

}
