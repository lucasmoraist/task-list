package com.lucasmoraist.comments.service;

import com.lucasmoraist.comments.domain.dto.CommentUpdate;
import com.lucasmoraist.comments.domain.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listAll();
    void update(Long id, CommentUpdate request);
    void delete(Long id);
}
