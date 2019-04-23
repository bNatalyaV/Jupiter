package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.RegionDao;
import id.bnv.jupiter.pojo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/region")
public class RegionController {
    private final RegionDao dao;

    @Autowired
    public RegionController(RegionDao regionDao) {dao=regionDao;}

    @GetMapping(value = "/all")
    public ResponseEntity getAllRegions() {
        List<Region> regionsList=dao.getAllRegions();
        return ResponseEntity.ok(regionsList);
    }
}