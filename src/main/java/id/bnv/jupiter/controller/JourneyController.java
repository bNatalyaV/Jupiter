package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.JourneyDao;
import id.bnv.jupiter.pojo.Journey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/journey")
public class JourneyController {
    private final JourneyDao journeyDao;

    @Autowired
    public JourneyController(JourneyDao journeyDao) {this.journeyDao=journeyDao;}

    @GetMapping(value = "/{idjourney}")
    public ResponseEntity getJourney(@PathVariable (value = "idjourney") int idJourney) {
        Journey journey =journeyDao.getJourney(idJourney);
        return ResponseEntity.ok(journey);
    }
}
