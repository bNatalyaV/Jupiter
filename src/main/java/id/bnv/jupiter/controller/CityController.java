package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.CityDao;
import id.bnv.jupiter.pojo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    private final CityDao cityDao;

    @Autowired
    public CityController(CityDao cityDao) {this.cityDao=cityDao;}

    @GetMapping(value = "/all")
    public ResponseEntity getAllCity() {
        List<City> cityList= cityDao.getAllCity();
        return ResponseEntity.ok(cityList);
    }
}