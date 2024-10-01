package com.lucasmoraist.comments.service.impl;

import com.lucasmoraist.comments.domain.dto.CommentRequest;
import com.lucasmoraist.comments.domain.entity.Comment;
import com.lucasmoraist.comments.repository.CommentRepository;
import com.lucasmoraist.comments.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Transactional
    @Override
    public Long create(Long taskId, CommentRequest request) {
        Comment comment = Comment.builder()
                .text(request.text())
                .taskId(taskId)
                .build();

        this.repository.save(comment);
        log.info("Comment created: {}", comment);

        return comment.getId();
    }

    @Transactional
    @Override
    public List<Comment> listAll() {
        log.info("Listing all comments");
        return this.repository.findAll();
    }

    @Override
    public void update(Long id, CommentRequest request) {
        log.info("Updating comment with id: {}", id);

        Comment comment = this.findById(id);

        comment.setText(request.text());
        this.repository.save(comment);

        log.info("Comment updated: {}", comment);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting comment with id: {}", id);
        Comment comment = this.findById(id);
        this.repository.delete(comment);
        log.info("Comment deleted: {}", comment);
    }

    private Comment findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }
}
