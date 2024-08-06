package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.model.dto.TaskResponse;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
@Slf4j
public class UpdateStatusTaskController {

    @Autowired
    private TaskService service;

    @Operation(summary = "Update task status", description = "Update the status of a task by its id")
    @Parameter(name = "id", description = "Task id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskNotFound.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                            	"msg": "Fill in all mandatory fields",
                            	"status": "BAD_REQUEST"
                            }
                            """)
            ))
    })
    @PatchMapping("status/{id}")
    public ResponseEntity<TaskResponse> updateTitle(@PathVariable Long id, @RequestBody TaskRequest request) {
        log.info("Getting task by id: {}", id);
        log.info("Request: {}", request);
        TaskResponse response = this.service.updateStatusTask(id, request);
        log.info("Response: {}", response);
        return ResponseEntity.ok().body(response);
    }

}
