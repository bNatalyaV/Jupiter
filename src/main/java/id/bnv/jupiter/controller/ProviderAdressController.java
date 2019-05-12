package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.ProviderAdressDao;
import id.bnv.jupiter.pojo.Adress;
import id.bnv.jupiter.pojo.Provider;
import id.bnv.jupiter.pojo.ProviderAdress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/adresses")
public class ProviderAdressController {
    private final ProviderAdressDao providerAdressDao;

    @Autowired
    public ProviderAdressController (ProviderAdressDao providerAdressDao) {
        this.providerAdressDao=providerAdressDao;
    }
    @GetMapping(value = "/{cityId}/{providerId}")
    public ResponseEntity getAdresses(@PathVariable int cityId,
                                      @PathVariable int providerId) {
        List<Adress> list=providerAdressDao.getAdress(cityId, providerId);
        return ResponseEntity.ok(list);
    }
}
