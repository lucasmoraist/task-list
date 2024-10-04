package com.lucasmoraist.comments.infra.amqp;

import com.lucasmoraist.comments.domain.dto.CommentReceived;
import com.lucasmoraist.comments.domain.entity.Comment;
import com.lucasmoraist.comments.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentsListener {

    @Autowired
    private CommentRepository repository;

    @RabbitListener(queues = "task.adding-comment")
    public void addComment(CommentReceived message) {
        Comment comment = Comment.builder()
                .text(message.text())
                .taskId(message.taskId())
                .build();

        this.repository.save(comment);
        log.info("Comment created: {}", comment);
    }

}
