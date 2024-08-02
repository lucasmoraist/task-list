package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class ListTaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<Page<EntityModel<Task>>> listTask(@RequestParam(required = false) String title, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(this.service.listTask(title, pageable));
    }

}
