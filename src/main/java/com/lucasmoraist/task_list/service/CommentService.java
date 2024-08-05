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

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private CommentRepository repository;

    @Autowired
    private TaskService taskService;


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

    public List<Comment> listComments() {
        logger.info("Listing all comments");

        List<Comment> comments = this.repository.findAll();

        logger.info("Found {} comments", comments.size());

        return comments;
    }

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

    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        logger.info("Updating comment with ID: {}", commentId);

        Comment comment = this.findComment(commentId);
        comment.setText(request.text());
        this.repository.save(comment);

        logger.info("Comment with ID: {} updated", commentId);

        return new CommentResponse(comment.getId());
    }

    public void deleteComment(Long commentId) {
        logger.info("Deleting comment with ID: {}", commentId);

        Comment comment = this.findComment(commentId);
        this.repository.delete(comment);

        logger.info("Comment with ID: {} deleted", commentId);
    }
}
