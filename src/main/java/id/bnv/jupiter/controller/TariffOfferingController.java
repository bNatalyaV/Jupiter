package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.TariffOfferingDao;
import id.bnv.jupiter.pojo.OffersAndAddOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tariffoffers")
public class TariffOfferingController {
    private final TariffOfferingDao tariffOfferingDao;

    @Autowired
    public TariffOfferingController(TariffOfferingDao tariffOfferingDao) {
        this.tariffOfferingDao = tariffOfferingDao;
    }

    @GetMapping(value = "/{tariffId}")
    public ResponseEntity getOffers(@PathVariable int tariffId) {
        List<OffersAndAddOffers> list = tariffOfferingDao.getOffersAndAddOffers(tariffId);

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/offers/{tariffId}")
    public ResponseEntity getOfferNamePriceQuantity(@PathVariable int tariffId,
                                                    @RequestHeader(value = "token") String token,
                                                    @RequestHeader(value = "userid") String userId) {
        List<OffersAndAddOffers> list = tariffOfferingDao.getOfferNamePriceQuantity(tariffId);

        return ResponseEntity.ok(list);
    }
}
