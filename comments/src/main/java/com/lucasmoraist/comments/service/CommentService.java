package com.lucasmoraist.comments.service;

import com.lucasmoraist.comments.domain.dto.CommentRequest;
import com.lucasmoraist.comments.domain.entity.Comment;

import java.util.List;

public interface CommentService {
    Long create(Long taskId, CommentRequest request);
    List<Comment> listAll();
    void update(Long id, CommentRequest request);
    void delete(Long id);
}
