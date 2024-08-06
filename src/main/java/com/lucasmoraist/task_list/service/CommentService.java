package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.CommentRequest;
import com.lucasmoraist.task_list.model.dto.CommentResponse;
import com.lucasmoraist.task_list.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Comment entity.
 * This class is responsible for handling the business logic of the application.
 * It is responsible for creating, updating, listing and deleting comments.
 *
 * @author lucasmoraist
 */
@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private CommentRepository repository;

    @Autowired
    private TaskService taskService;

    /**
     * Creates a new comment for a task.
     *
     * @param taskId  ID of the task the comment is related to.
     * @param request CommentRequest object containing the comment information.
     * @return CommentResponse object containing the ID of the created comment.
     */
    public CommentResponse create(Long taskId, CommentRequest request) {
        logger.info("Creating comment for task ID: {}", taskId);

        Task task = this.taskService.findTask(taskId);
        Comment comment = Comment.builder()
                .text(request.text())
                .task(task)
                .build();

        this.repository.save(comment);

        logger.info("Comment created with ID: {} for task ID: {}", comment.getId(), taskId);

        return new CommentResponse(comment.getId());
    }

    /**
     * Lists all comments in the database.
     *
     * @return List of all comments.
     */
    public List<Comment> listComments() {
        logger.info("Listing all comments");

        List<Comment> comments = this.repository.findAll();

        logger.info("Found {} comments", comments.size());

        return comments;
    }

    /**
     * Finds a comment by its ID.
     *
     * @param commentId ID of the comment to be found.
     * @return Comment object with the specified ID.
     */
    public Comment findComment(Long commentId) {
        logger.info("Finding comment with ID: {}", commentId);

        Comment comment = this.repository.findById(commentId)
                .orElseThrow(() -> {
                    logger.error("Comment with ID: {} not found", commentId);
                    return new CommentsNotFound();
                });

        logger.info("Comment with ID: {} found", commentId);

        return comment;
    }

    /**
     * Updates a comment with the specified ID.
     *
     * @param commentId ID of the comment to be updated.
     * @param request   CommentRequest object containing the new comment information.
     * @return CommentResponse object containing the ID of the updated comment.
     */
    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        logger.info("Updating comment with ID: {}", commentId);

        Comment comment = this.findComment(commentId);
        comment.setText(request.text());
        this.repository.save(comment);

        logger.info("Comment with ID: {} updated", commentId);

        return new CommentResponse(comment.getId());
    }

    /**
     * Deletes a comment with the specified ID.
     *
     * @param commentId ID of the comment to be deleted.
     */
    public void deleteComment(Long commentId) {
        logger.info("Deleting comment with ID: {}", commentId);

        Comment comment = this.findComment(commentId);
        this.repository.delete(comment);

        logger.info("Comment with ID: {} deleted", commentId);
    }
}
