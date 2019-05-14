package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.TaskDao;
import id.bnv.jupiter.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    private final TaskDao taskDao;

    @Autowired
    public TaskController(TaskDao taskDao) {this.taskDao=taskDao;}

    @GetMapping(value = "/{idTask}")
    public ResponseEntity getTask (@PathVariable int idTask,
                                   @RequestHeader(value = "token") String token,
                                   @RequestHeader(value = "userid") String userId) {
        Task task = taskDao.getTask(idTask);
        return ResponseEntity.ok(task);
    }
}
