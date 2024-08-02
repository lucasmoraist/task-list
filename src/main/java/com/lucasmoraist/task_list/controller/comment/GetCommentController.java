package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.model.dto.CommentResponse;
import com.lucasmoraist.task_list.service.CommentService;
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

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
public class GetCommentController {

    @Autowired
    private CommentService service;

    @Operation(summary = "List all comments", description = "List all comments")
    @Parameter(name = "commentId", description = "The comment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class)
            )),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentsNotFound.class)
            ))
    })
    @GetMapping("{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId){
        Comment comment =  this.service.findComment(commentId);
        return ResponseEntity.ok().body(comment);
    }

}
