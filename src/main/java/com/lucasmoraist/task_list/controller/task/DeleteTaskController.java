package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to manage the deletion of tasks.
 *
 * @author lucasmoraist
 */
@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
@Slf4j
public class DeleteTaskController {

    @Autowired
    private TaskService service;

    /**
     * Delete a task with the given id.
     * @param id The id of the task to be deleted
     * @throws TaskNotFound If the task with the given id is not found
     */
    @Operation(summary = "Delete a task", description = "Delete a task with the given id")
    @Parameter(name = "id", description = "The id of the task to be deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskNotFound.class)
            ))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deleting task with id {}", id);
        this.service.deleteTask(id);
        log.info("Task with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }

}
