package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class DeleteTaskController {

    @Autowired
    private TaskService service;

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
