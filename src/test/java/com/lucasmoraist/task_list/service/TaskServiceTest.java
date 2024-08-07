package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.model.dto.TaskResponse;
import com.lucasmoraist.task_list.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskRequest taskRequest;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        taskRequest = new TaskRequest("Test Title", "Test Description", StatusType.PENDING);
        task = new Task(taskRequest);
        task.setId(1L);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("createTaskIsSuccess: Deve criar uma tarefa com sucesso")
    void case01() {
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);
            return savedTask;
        });

        doNothing().when(emailService).sendEmail(eq("creating"), any(Task.class), isNull());

        TaskResponse response = taskService.createTask(taskRequest);

        assertNotNull(response);
        assertEquals(task.getId(), response.taskId());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(emailService, times(1)).sendEmail(eq("creating"), any(Task.class), isNull());
    }

    @Test
    @DisplayName("createTaskIsNotSuccess: Deve lançar exceção ao criar tarefa com título vazio")
    void case02() {
        TaskRequest invalidRequest = new TaskRequest("", "Description", StatusType.PENDING);

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(invalidRequest));
    }

    @Test
    @DisplayName("listTaskSortByIsSuccess: Deve listar tarefas ordenadas por título em ordem ascendente")
    void case03() {
        when(taskRepository.findAll(any(Sort.class))).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.listTaskSortBy("title", "asc");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    @DisplayName("listTaskSortByIsNotSuccess: Deve lançar exceção ao listar tarefas com parâmetro de ordenação inválido")
    void case04() {
        assertThrows(IllegalArgumentException.class, () -> taskService.listTaskSortBy("invalid", "asc"));
    }

    @Test
    @DisplayName("findAllIsSuccess: Deve listar tarefas paginadas")
    void case05() {
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(taskPage);

        Page<EntityModel<Task>> result = taskService.listTaskPage(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(taskRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findTaskIsSuccess: Deve encontrar uma tarefa pelo ID")
    void case06() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        Task foundTask = taskService.findTask(1L);

        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("findTaskIsNotSuccess: Deve lançar exceção ao não encontrar uma tarefa pelo ID")
    void case07() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.findTask(1L));
    }

    @Test
    @DisplayName("updateTitleTaskIsSuccess: Deve atualizar o título da tarefa")
    void case08() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        doNothing().when(emailService).sendEmail(eq("updating"), any(Task.class), isNull());

        TaskResponse response = taskService.updateTitleTask(1L, new TaskRequest("Updated Title", null, null));

        assertNotNull(response);
        assertEquals(task.getId(), response.taskId());
        assertEquals("Updated Title", task.getTitle());
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(emailService, times(1)).sendEmail(eq("updating"), any(Task.class), isNull());
    }

    @Test
    @DisplayName("updateTitleTaskIsNotSuccess: Deve lançar exceção ao atualizar título da tarefa inexistente")
    void case09() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.updateTitleTask(1L, new TaskRequest("Updated Title", null, null)));
    }

    @Test
    @DisplayName("updateDescriptionTaskIsSuccess: Deve atualizar a descrição da tarefa")
    void case10() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        doNothing().when(emailService).sendEmail(eq("updating"), any(Task.class), isNull());

        TaskResponse response = taskService.updateDescriptionTask(1L, new TaskRequest(null, "Updated Description", null));

        assertNotNull(response);
        assertEquals(task.getId(), response.taskId());
        assertEquals("Updated Description", task.getDescription());
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(emailService, times(1)).sendEmail(eq("updating"), any(Task.class), isNull());
    }

    @Test
    @DisplayName("updateDescriptionTaskIsNotSuccess: Deve lançar exceção ao atualizar descrição da tarefa inexistente")
    void case11() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.updateDescriptionTask(1L, new TaskRequest(null, "Updated Description", null)));
    }

    @Test
    @DisplayName("updateStatusTaskIsSuccess: Deve atualizar o status da tarefa")
    void case12() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        doNothing().when(emailService).sendEmail(eq("updating"), any(Task.class), isNull());

        TaskResponse response = taskService.updateStatusTask(1L, new TaskRequest(null, null, StatusType.DONE));

        assertNotNull(response);
        assertEquals(task.getId(), response.taskId());
        assertEquals(StatusType.DONE, task.getStatus());
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(emailService, times(1)).sendEmail(eq("updating"), any(Task.class), isNull());
    }

    @Test
    @DisplayName("updateStatusTaskIsNotSuccess: Deve lançar exceção ao atualizar status da tarefa inexistente")
    void case13() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.updateStatusTask(1L, new TaskRequest(null, null, StatusType.DONE)));
    }

    @Test
    @DisplayName("deleteTaskIsSuccess: Deve deletar a tarefa pelo ID")
    void case14() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        doNothing().when(emailService).sendEmail(anyString(), any(Task.class), any(LocalDateTime.class));
        doNothing().when(taskRepository).delete(any(Task.class));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).delete(any(Task.class));
        verify(emailService, times(1)).sendEmail(eq("deleting"), any(Task.class), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("deleteTaskIsNotSuccess: Deve lançar exceção ao deletar tarefa inexistente")
    void case15() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.deleteTask(1L));
    }

}