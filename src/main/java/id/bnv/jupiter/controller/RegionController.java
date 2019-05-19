package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.RegionDao;
import id.bnv.jupiter.pojo.Provider;
import id.bnv.jupiter.pojo.ProviderIdAndName;
import id.bnv.jupiter.pojo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/region")
public class RegionController {
    private final RegionDao dao;

    @Autowired
    public RegionController(RegionDao regionDao) {
        dao = regionDao;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllRegions(
            @RequestHeader(value = "token") String token,
            @RequestHeader(value = "userid") String userId) {
        List<Region> regionsList = dao.getAllRegions();
        return ResponseEntity.ok(regionsList);
    }

    @GetMapping(value = "/providersForRegion/{regionid}")
    public ResponseEntity getProvidersForRegion(@PathVariable(value = "regionid") int idRegion,
                                                @RequestHeader(value = "token") String token,
                                                @RequestHeader(value = "userid") String userId) {
        Set<Provider> providers = dao.getAllProvidersByRegionId(idRegion);
        return ResponseEntity.ok(providers);
    }
}
