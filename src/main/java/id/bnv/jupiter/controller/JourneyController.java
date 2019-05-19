package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.JourneyDao;
import id.bnv.jupiter.json.Response;
import id.bnv.jupiter.pojo.FullInfoAboutTarif;
import id.bnv.jupiter.pojo.Journey;
import id.bnv.jupiter.pojo.JourneysAndTasks;
import id.bnv.jupiter.pojo.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/journey")
public class JourneyController {
    private final JourneyDao journeyDao;

    @Autowired
    public JourneyController(JourneyDao journeyDao) {
        this.journeyDao = journeyDao;
    }

    @GetMapping(value = "/{idjourney}")
    public ResponseEntity getJourney(@PathVariable(value = "idjourney") int idJourney,
                                     @RequestHeader(value = "token") String token,
                                     @RequestHeader(value = "userid") String userId) {
        Journey journey = journeyDao.getJourney(idJourney);
        return ResponseEntity.ok(journey);
    }

    @GetMapping(value = "/completed/{userId}")
    public ResponseEntity getCompletedNumbers(@PathVariable int userId,
                                              @RequestHeader(value = "token") String token,
                                              @RequestHeader(value = "userid") String userid) {
        List<PhoneNumber> list = journeyDao.getCompletedNumbers(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/completedjourneys/{userId}")
    public ResponseEntity getCompletedJourneys(@PathVariable int userId,
                                               @RequestHeader(value = "token") String token,
                                               @RequestHeader(value = "userid") String userid) {
        List<Journey> list = journeyDao.getCompletedJourneys(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/uncompleted/{userId}")
    public ResponseEntity getUncompletedNumbers(@PathVariable int userId,
                                                @RequestHeader(value = "token") String token,
                                                @RequestHeader(value = "userid") String userid) {
        List<PhoneNumber> list = journeyDao.getUncompletedJourneys(userId);
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/new/{numberId}/{tariffId}")
    public ResponseEntity addJourney(@PathVariable int numberId,
                                     @PathVariable int tariffId) throws Exception {
        journeyDao.addJourney(numberId, tariffId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/continue/{numberId}/{tariffId}/{journeyId}")
    public ResponseEntity continueJourney(@PathVariable int numberId,
                                          @PathVariable int tariffId,
                                          @PathVariable int journeyId,
                                          @RequestHeader(value = "token") String token,
                                          @RequestHeader(value = "userid") String userId) throws Exception {
        journeyDao.addTasksFromSecondToSix(numberId, tariffId, journeyId);
        return ResponseEntity.ok(new Response("Journey was finished"));
    }

    @GetMapping(value = "/all/{userId}")
    public ResponseEntity getInfoAboutAllJourneys(@PathVariable int userId,
                                                  @RequestHeader(value = "token") String token,
                                                  @RequestHeader(value = "userid") String userid) {
        List<FullInfoAboutTarif> list = journeyDao.getInfoAboutJourneys(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/alltasks/{userId}")
    public ResponseEntity getAllTasksByUserId(@PathVariable int userId,
                                              @RequestHeader(value = "token") String token,
                                              @RequestHeader(value = "userid") String userid) {
        List<JourneysAndTasks> list = journeyDao.getTasksByUserId(userId);
        return ResponseEntity.ok(list);
    }
}
