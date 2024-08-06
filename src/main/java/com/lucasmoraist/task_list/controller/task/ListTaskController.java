package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Controller responsible for listing tasks
 *
 * @author lucasmoraist
 */
@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
@Slf4j
public class ListTaskController {

    @Autowired
    private TaskService service;

    /**
     * List tasks
     * @param status Status of the tasks to be listed
     * @param pageable Pageable object to control pagination
     * @return List of tasks with the given status
     */
    @Operation(summary = "List tasks", description = "List all tasks or filter by title")
    @Parameter(name = "title", description = "The title to filter tasks")
    @ApiResponse(responseCode = "200", description = "Tasks listed successfully", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Task.class)
    ))
    @GetMapping
    public ResponseEntity<Page<EntityModel<Task>>> listTask(@RequestParam(required = false) StatusType status, @PageableDefault(size = 5) Pageable pageable){
        log.info("Listing tasks with status {}", status);
        log.info("Pageable: {}", pageable);
        log.info("List tasks is successfully");
        return ResponseEntity.ok(this.service.listTaskPage(status, pageable));
    }

}
