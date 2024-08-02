package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.exceptions.ExceptionDTO;
import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
public class GetTaskController {

    @Autowired
    private TaskService service;

    @Operation(summary = "Get a task", description = "Get a task with the given id")
    @Parameter(name = "id", description = "The id of the task to be retrieved")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Task.class)
            )),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskNotFound.class)
            ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findTask(id));
    }

}
