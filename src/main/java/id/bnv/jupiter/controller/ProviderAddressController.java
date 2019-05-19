package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.ProviderAdressDao;
import id.bnv.jupiter.pojo.Adress;
import id.bnv.jupiter.pojo.Provider;
import id.bnv.jupiter.pojo.ProviderAdress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/adresses")
public class ProviderAddressController {
    private final ProviderAdressDao providerAdressDao;

    @Autowired
    public ProviderAddressController(ProviderAdressDao providerAdressDao) {
        this.providerAdressDao = providerAdressDao;
    }

    @GetMapping(value = "/{cityId}/{providerId}")
    public ResponseEntity getAddresses(@PathVariable int cityId,
                                       @PathVariable int providerId,
                                       @RequestHeader(value = "token") String token,
                                       @RequestHeader(value = "userid") String userId) {
        List<Adress> list = providerAdressDao.getAdress(cityId, providerId);
        return ResponseEntity.ok(list);
    }
}
