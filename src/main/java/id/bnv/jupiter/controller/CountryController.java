package id.bnv.jupiter.controller;

import com.jcraft.jsch.Session;
import id.bnv.jupiter.dao.CountryDao;
import id.bnv.jupiter.pojo.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryController {
    private final CountryDao countryDao;

    @Autowired
    public CountryController(CountryDao countryDao) {this.countryDao=countryDao;}

    @GetMapping(value = "/all")
    public ResponseEntity getAllCountry() {
        List<Country> countryList= countryDao.getAllCountries();
        return ResponseEntity.ok(countryList);
    }
}
