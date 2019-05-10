package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.JourneyDao;
import id.bnv.jupiter.pojo.FullInfoAboutTarif;
import id.bnv.jupiter.pojo.Journey;
import id.bnv.jupiter.pojo.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @GetMapping(value = "/completed/{userId}")
    public ResponseEntity getCompletedNumbers(@PathVariable int userId) {
        Set<PhoneNumber> list=journeyDao.getCompletedNumbers(userId);
        return ResponseEntity.ok(list);
    }
    @PostMapping(value = "/new/{numberId}/{tariffId}")
    public ResponseEntity addJourney(@PathVariable int numberId,
                                     @PathVariable int tariffId) {
        Journey journey=journeyDao.addJourney(numberId, tariffId);
        return ResponseEntity.ok(journey);
    }
    @GetMapping(value = "/all/{userId}")
    public ResponseEntity getInfoAboutAllJourneys(@PathVariable int userId) {
        List<FullInfoAboutTarif> list=journeyDao.getInfoAboutJourneys(userId);
        return ResponseEntity.ok(list);
    }
    @PostMapping(value = "/1")
    public ResponseEntity full() {
        journeyDao.fullOldTariffId();
        return ResponseEntity.ok().body("Ok");
    }
}
