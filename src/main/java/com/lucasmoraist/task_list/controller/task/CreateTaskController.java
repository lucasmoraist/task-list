package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.dto.TaskResponse;
import com.lucasmoraist.task_list.dto.TaskRequest;
import com.lucasmoraist.task_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class CreateTaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request){
        TaskResponse response = this.service.create(request);
        return ResponseEntity.ok().body(response);
    }

}
