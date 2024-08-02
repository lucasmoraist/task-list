package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.dto.TaskResponse;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
public class CreateTaskController {

    @Autowired
    private TaskService service;

    @Operation(summary = "Create a task",description = "Create a task with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                            	"msg": "Fill in all mandatory fields",
                            	"status": "BAD_REQUEST"
                            }
                            """)
            ))
    })
    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request){
        TaskResponse response = this.service.create(request);
        return ResponseEntity.ok().body(response);
    }

}
