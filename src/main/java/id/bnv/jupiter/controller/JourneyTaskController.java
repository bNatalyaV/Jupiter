package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.JourneyTaskDao;
import id.bnv.jupiter.pojo.JourneyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/journeytask")
public class JourneyTaskController {
    private final JourneyTaskDao journeyTaskDao;

    @Autowired
    public JourneyTaskController(JourneyTaskDao journeyTaskDao) {
        this.journeyTaskDao = journeyTaskDao;
    }
    @GetMapping(value = "/{idjourneytask}")
    public ResponseEntity getJourneyTask(@PathVariable (value = "idjourneytask")
                                                     int idJourneyTask) {
        JourneyTask journeyTask=journeyTaskDao.getJourneyTask(idJourneyTask);
        return ResponseEntity.ok(journeyTask);
    }


}
