package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.dto.TaskRequest;
import com.lucasmoraist.task_list.dto.TaskResponse;
import com.lucasmoraist.task_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class UpdateDescrTaskController {

    @Autowired
    private TaskService service;

    @PatchMapping("description/{id}")
    public ResponseEntity<TaskResponse> updateDescription(@PathVariable Long id, @RequestBody TaskRequest request) {
        TaskResponse response = this.service.updateDescriptionTask(id, request);
        return ResponseEntity.ok(response);
    }

}
