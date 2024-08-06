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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
public class ListByStatusController {

    @Autowired
    private TaskService service;

    @Operation(summary = "List tasks by status", description = "List all tasks with the given status")
    @ApiResponse(responseCode = "200", description = "Tasks found", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Task.class)
    ))
    @Parameter(name = "status", description = "Status of the tasks to be listed", required = true)
    @GetMapping("status")
    public ResponseEntity<List<Task>> listByStatus(@RequestParam(name = "status") StatusType status) {
        List<Task> tasks = this.service.findTasksByStatus(status);
        return ResponseEntity.ok().body(tasks);
    }

}
