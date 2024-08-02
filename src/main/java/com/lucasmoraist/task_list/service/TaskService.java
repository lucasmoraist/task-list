package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.dto.TaskResponse;
import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final EmailService emailService;

    public TaskService(TaskRepository repository, EmailService emailService) {
        this.emailService = emailService;
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request){
        Task task = new Task(request);
        this.repository.save(task);

        this.emailService.sendEmailToCreatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedAt());

        return new TaskResponse(task.getId());
    }

    public Page<EntityModel<Task>> listTask(StatusType status, Pageable pageable){
        Page<Task> statu = (status == null)?
                this.repository.findAll(pageable):
                this.repository.findByTitleContaining(status, pageable);

        return statu.map(Task::toEntityModel);
    }

    public Task findTask(Long id){
        return this.repository.findById(id)
                .orElseThrow(TaskNotFound::new);
    }

    public TaskResponse updateTitleTask(Long id, TaskRequest request){
        Task task = this.findTask(id);
        task.setTitle(request.title());
        this.repository.save(task);

        this.emailService.sendEmailToUpdatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getUpdatedAt());
        return new TaskResponse(task.getId());
    }

    public TaskResponse updateDescriptionTask(Long id, TaskRequest request){
        Task task = this.findTask(id);
        task.setDescription(request.description());
        this.repository.save(task);

        this.emailService.sendEmailToUpdatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getUpdatedAt());
        return new TaskResponse(task.getId());
    }

    public TaskResponse updateStatusTask(Long id, Task task){
        Task taskToUpdate = this.findTask(id);
        taskToUpdate.setStatus(task.getStatus());
        this.repository.save(taskToUpdate);

        this.emailService.sendEmailToUpdatingTask(taskToUpdate.getTitle(), taskToUpdate.getDescription(), taskToUpdate.getStatus(), taskToUpdate.getUpdatedAt());
        return new TaskResponse(taskToUpdate.getId());

    }

    public void deleteTask(Long id){
        Task task = this.findTask(id);
        this.emailService.sendEmailToDeletingTask(task.getTitle(), task.getDescription(), task.getStatus(), LocalDateTime.now());
        this.repository.delete(task);
    }

}
