package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.dto.CommentRequest;
import com.lucasmoraist.task_list.model.dto.CommentResponse;
import com.lucasmoraist.task_list.service.CommentService;
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

/**
 * Controller responsible for creating a comment for a task.
 *
 * @author lucasmoraist
 */
@RestController
@RequestMapping("/tasks")
@Tag(name = "Comment")
@Slf4j
public class CreateCommentController {

    @Autowired
    private CommentService service;

    /**
     * Create a comment for a task.
     * @param taskId The task id
     * @param request The comment request
     * @return The created comment
     * @throws TaskNotFound If the task is not found
     * @throws IllegalArgumentException If the request is invalid
     */
    @Operation(summary = "Create a comment for a task", description = "Create a comment for a task")
    @Parameter(name = "taskId", description = "The task id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskNotFound.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                            	"msg": "Fill in all mandatory fields",
                            	"status": "BAD_REQUEST"
                            }
                            """)

            ))
    })
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long taskId, @Valid @RequestBody CommentRequest request) {
        log.info("Creating comment for task with ID: {}", taskId);
        log.info("Request: {}", request);
        CommentResponse comment = this.service.create(taskId, request);
        log.info("Comment created: {}", comment);
        return ResponseEntity.ok().body(comment);
    }

}
