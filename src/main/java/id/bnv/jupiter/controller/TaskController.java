package id.bnv.jupiter.controller;

import id.bnv.jupiter.dao.TaskDao;
import id.bnv.jupiter.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    private final TaskDao taskDao;

    @Autowired
    public TaskController(TaskDao taskDao) {this.taskDao=taskDao;}

    @GetMapping(value = "/{idTask}")
    public ResponseEntity getTask (@PathVariable int idTask) {
        Task task = taskDao.getTask(idTask);
        return ResponseEntity.ok(task);
    }
}
