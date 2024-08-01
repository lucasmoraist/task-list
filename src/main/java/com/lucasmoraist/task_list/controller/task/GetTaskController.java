package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class GetTaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findTask(id));
    }

}
