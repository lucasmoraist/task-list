package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.service.CommentService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for getting a comment.
 *
 * @author lucasmoraist
 */
@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
@Slf4j
public class GetCommentController {

    @Autowired
    private CommentService service;

    /**
     * Get a comment by its id.
     * @param commentId The comment id
     * @return The comment
     * @throws CommentsNotFound If the comment is not found
     */
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
        log.info("Getting comment with id: {}", commentId);
        Comment comment =  this.service.findComment(commentId);
        log.info("Comment found: {}", comment);
        return ResponseEntity.ok().body(comment);
    }

}
