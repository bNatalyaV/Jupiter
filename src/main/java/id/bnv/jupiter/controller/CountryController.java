package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.CountryDao;
import id.bnv.jupiter.pojo.Country;
import id.bnv.jupiter.pojo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryController {
    private final CountryDao countryDao;

    @Autowired
    public CountryController(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllCountry() {
        List<Country> countryList = countryDao.getAllCountries();
        return ResponseEntity.ok(countryList);
    }

    @GetMapping(value = "/{idcountry}")
    public ResponseEntity getRegionsByCountryId(@PathVariable(value = "idcountry") int countryId) {
        List<Region> regionList = countryDao.getAllRegionsForCountry(countryId);
        return ResponseEntity.ok(regionList);
    }
}
