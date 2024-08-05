package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
@Slf4j
public class DeleteCommentController {

    @Autowired
    private CommentService service;

    @Operation(summary = "Delete a comment", description = "Delete a comment")
    @Parameter(name = "commentId", description = "The comment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted", content = @Content(
                    mediaType = "application/json"
            )),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentsNotFound.class)
            )),
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        log.info("Deleting comment with id: {}", commentId);
        this.service.deleteComment(commentId);
        log.info("Comment deleted");
        return ResponseEntity.noContent().build();
    }

}
