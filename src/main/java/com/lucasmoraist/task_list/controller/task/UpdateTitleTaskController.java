package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.dto.TaskRequest;
import com.lucasmoraist.task_list.dto.TaskResponse;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class UpdateTitleTaskController {

    @Autowired
    private TaskService service;

    @PatchMapping("title/{id}")
    public ResponseEntity<TaskResponse> updateTitle(@PathVariable Long id, @Valid @RequestBody TaskRequest request){
        TaskResponse response = this.service.updateTitleTask(id, request);
        return ResponseEntity.ok().body(response);
    }

}
