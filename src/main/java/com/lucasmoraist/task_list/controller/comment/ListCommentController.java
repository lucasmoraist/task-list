package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
public class ListCommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "List all comments", description = "List all comments")
    @ApiResponse(responseCode = "200", description = "List of comments")
    @GetMapping
    public ResponseEntity<List<Comment>> listComments(){
        return ResponseEntity.ok().body(this.commentService.listComments());
    }

}
