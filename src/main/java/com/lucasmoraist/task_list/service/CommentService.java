package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.CommentRequest;
import com.lucasmoraist.task_list.model.dto.CommentResponse;
import com.lucasmoraist.task_list.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private TaskService taskService;

    public CommentResponse create(Long taskId, CommentRequest request){
        Task task = this.taskService.findTask(taskId);
        Comment comment = Comment.builder()
                .text(request.text())
                .task(task)
                .build();
        this.repository.save(comment);
        return new CommentResponse(comment.getId());
    }

    public List<Comment> listComments(){
        return this.repository.findAll();
    }

    public Comment findComment(Long commentId){
        return this.repository.findById(commentId)
                .orElseThrow(CommentsNotFound::new);
    }

    public CommentResponse updateComment(Long commentId, CommentRequest request){
        Comment comment = this.findComment(commentId);
        comment.setText(request.text());
        this.repository.save(comment);
        return new CommentResponse(comment.getId());
    }

    public void deleteComment(Long commentId){
        Comment comment = this.findComment(commentId);
        this.repository.delete(comment);
    }

}
