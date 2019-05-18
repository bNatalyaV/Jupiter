package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.JourneyTaskDao;
import id.bnv.jupiter.pojo.JourneyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/journeytask")
public class JourneyTaskController {
    private final JourneyTaskDao journeyTaskDao;

    @Autowired
    public JourneyTaskController(JourneyTaskDao journeyTaskDao) {
        this.journeyTaskDao = journeyTaskDao;
    }

    @GetMapping(value = "/{idjourneytask}")
    public ResponseEntity getJourneyTask(@PathVariable(value = "idjourneytask")
                                                 int idJourneyTask,
                                         @RequestHeader(value = "token") String token,
                                         @RequestHeader(value = "userid") String userId) {
        JourneyTask journeyTask = journeyTaskDao.getJourneyTask(idJourneyTask);
        return ResponseEntity.ok(journeyTask);
    }


}
