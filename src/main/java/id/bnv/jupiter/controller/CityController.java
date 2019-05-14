package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.CityDao;
import id.bnv.jupiter.pojo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    private final CityDao cityDao;

    @Autowired
    public CityController(CityDao cityDao) {this.cityDao=cityDao;}

    @GetMapping(value = "/all")
    public ResponseEntity getAllCity(
            @RequestHeader(value = "token") String token,
            @RequestHeader(value = "userid") String userId) {
        List<City> cityList= cityDao.getAllCity();
        return ResponseEntity.ok(cityList);
    }

    @GetMapping(value = "/cities/{regionId}")
    public ResponseEntity getCitiesByRegionId(@PathVariable int regionId,
                                              @RequestHeader(value = "token") String token,
                                              @RequestHeader(value = "userid") String userId) {
        List<City> list=cityDao.getCityByRegionId(regionId);
        return ResponseEntity.ok(list);
    }
}