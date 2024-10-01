package com.lucasmoraist.comments.controller;

import com.lucasmoraist.comments.domain.dto.CommentRequest;
import com.lucasmoraist.comments.domain.entity.Comment;
import com.lucasmoraist.comments.service.CommentService;
import com.netflix.discovery.converters.Auto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping
    public ResponseEntity<List<Comment>> listComments(){
        log.info("Listing all comments");
        return ResponseEntity.ok().body(this.service.listAll());
    }
    
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long taskId, @Valid @RequestBody CommentRequest request) {
        log.info("Creating comment for task with ID: {}", taskId);
        log.info("Request: {}", request);
        this.service.create(taskId, request);
        log.info("Comment created");
        return ResponseEntity.ok().build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequest request) {
        log.info("Updating comment with id: {}", commentId);
        log.info("Request: {}", request);
        this.service.update(commentId, request);
        log.info("Comment update");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        log.info("Deleting comment with id: {}", commentId);
        this.service.delete(commentId);
        log.info("Comment deleted");
        return ResponseEntity.noContent().build();
    }

}
