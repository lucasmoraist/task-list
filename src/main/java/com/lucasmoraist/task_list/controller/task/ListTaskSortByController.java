package com.lucasmoraist.task_list.controller.task;

import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ListTaskSortByController {

    @Autowired
    private TaskService service;

    @Operation(summary = "List tasks sorted by", description = "List all tasks sorted by the given field")
    @ApiResponse(responseCode = "200", description = "Tasks found", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Task.class)
    ))
    @Parameters(value = {
            @Parameter(name = "sortBy", description = "Field to sort by", required = true),
            @Parameter(name = "order", description = "Order of the sorting", required = true)
    })
    @GetMapping("title")
    public ResponseEntity<List<Task>> listTaskSortBy(@RequestParam(defaultValue = "title") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String order) {
        log.info("Listing tasks sorted by {} in order {}", sortBy, order);
        return ResponseEntity.ok(this.service.listTaskSortBy(sortBy, order));
    }


}
