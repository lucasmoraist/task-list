package com.lucasmoraist.task_list.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.lucasmoraist.task_list.exceptions.CommentsNotFound;
import com.lucasmoraist.task_list.model.Comment;
import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.CommentRequest;
import com.lucasmoraist.task_list.model.dto.CommentResponse;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentServiceTest {

    @Mock
    private CommentRepository repository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private CommentRequest commentRequest;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(new TaskRequest("Test Title", "Test Description", StatusType.PENDING));
        task.setId(1L);
        comment = Comment.builder()
                .id(1L)
                .text("Test Comment")
                .task(task)
                .build();
        commentRequest = new CommentRequest("Test Comment");
    }

    @Test
    @DisplayName("createIsSuccess: Deve criar um comentário com sucesso")
    void case01() {
        when(taskService.findTask(anyLong())).thenReturn(task);
        when(repository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1L);
            return savedComment;
        });

        CommentResponse response = commentService.create(1L, commentRequest);

        assertNotNull(response);
        assertEquals(comment.getId(), response.commentId());
        verify(taskService, times(1)).findTask(anyLong());
        verify(repository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("listCommentsIsSuccess: Deve listar todos os comentários com sucesso")
    void case02() {
        when(repository.findAll()).thenReturn(Collections.singletonList(comment));

        List<Comment> comments = commentService.listComments();

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals(comment.getId(), comments.get(0).getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findCommentIsSuccess: Deve encontrar um comentário pelo ID")
    void case03() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.findComment(1L);

        assertNotNull(foundComment);
        assertEquals(comment.getId(), foundComment.getId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("findCommentIsNotSuccess: Deve lançar exceção ao não encontrar um comentário pelo ID")
    void case04() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentsNotFound.class, () -> commentService.findComment(1L));
    }

    @Test
    @DisplayName("updateCommentIsSuccess: Deve atualizar um comentário com sucesso")
    void case05() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(repository.save(any(Comment.class))).thenReturn(comment);

        CommentResponse response = commentService.updateComment(1L, commentRequest);

        assertNotNull(response);
        assertEquals(comment.getId(), response.commentId());
        assertEquals(commentRequest.text(), comment.getText());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("updateCommentIsNotSuccess: Deve lançar exceção ao atualizar um comentário inexistente")
    void case06() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentsNotFound.class, () -> commentService.updateComment(1L, commentRequest));
    }

    @Test
    @DisplayName("deleteCommentIsSuccess: Deve deletar um comentário com sucesso")
    void case07() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(comment));
        doNothing().when(repository).delete(any(Comment.class));

        commentService.deleteComment(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Comment.class));
    }

    @Test
    @DisplayName("deleteCommentIsNotSuccess: Deve lançar exceção ao deletar um comentário inexistente")
    void case08() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentsNotFound.class, () -> commentService.deleteComment(1L));
    }

}