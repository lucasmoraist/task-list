package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class ListTaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> list(){
        return ResponseEntity.ok(this.service.listTask());
    }

}
