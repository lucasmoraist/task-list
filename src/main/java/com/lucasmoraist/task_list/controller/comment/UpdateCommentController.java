package com.lucasmoraist.task_list.controller.comment;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment")
public class UpdateCommentController {

    @Autowired
    private CommentService service;

    @Operation(summary = "Update a comment", description = "Update a comment by its id")
    @Parameter(name = "commentId", description = "The id of the comment to be updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentsNotFound.class)
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
    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequest request) {
        CommentResponse response = this.service.updateComment(commentId, request);
        return ResponseEntity.ok().body(response);
    }

}
