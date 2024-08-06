package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.exceptions.SendMailException;
import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.dto.TaskResponse;
import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import com.lucasmoraist.task_list.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"tasks"})
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final EmailService emailService;

    public TaskService(TaskRepository repository, EmailService emailService) {
        this.emailService = emailService;
        this.repository = repository;
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = this.create(request);
        return new TaskResponse(task.getId());
    }

    public List<Task> listTaskSortBy(String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Order.asc(sortBy)) :
                Sort.by(Sort.Order.desc(sortBy));
        return this.repository.findAll(sort);
    }

    @Caching(
            cacheable = @Cacheable(value = "tasks", key = "#status + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    )
    public Page<EntityModel<Task>> listTaskPage(StatusType status, Pageable pageable) {
        logger.info("Listing tasks with status: {}", status);
        Page<Task> tasks = (status == null) ?
                this.repository.findAll(pageable) :
                this.repository.findByStatusContaining(status, pageable);

        return tasks.map(Task::toEntityModel);
    }

    @Cacheable(value = "tasks", key = "#id")
    public Task findTask(Long id) {
        logger.info("Finding task with ID: {} from database", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Task with ID: {} not found", id);
                    return new TaskNotFound();
                });
    }

    public List<Task> findTasksByStatus(StatusType status) {
        logger.info("Finding tasks with status: {}", status);
        return this.repository.findTaskByStatus(status);
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#id")
    )
    public TaskResponse updateTitleTask(Long id, TaskRequest request) {
        logger.info("Updating title of task with ID: {}", id);
        Task task = this.findTask(id);
        task.setTitle(request.title());
        this.repository.save(task);

        logger.info("Sending email for task title update: {}", task.getTitle());
        this.emailService.sendEmailToUpdatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getUpdatedAt());
        return new TaskResponse(task.getId());
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#id")
    )
    public TaskResponse updateDescriptionTask(Long id, TaskRequest request) {
        logger.info("Updating description of task with ID: {}", id);
        Task task = this.findTask(id);
        task.setDescription(request.description());
        this.repository.save(task);

        logger.info("Sending email for task description update: {}", task.getTitle());
        this.emailService.sendEmailToUpdatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getUpdatedAt());
        return new TaskResponse(task.getId());
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#id")
    )
    public TaskResponse updateStatusTask(Long id, Task task) {
        logger.info("Updating status of task with ID: {}", id);
        Task taskToUpdate = this.findTask(id);
        taskToUpdate.setStatus(task.getStatus());
        this.repository.save(taskToUpdate);

        logger.info("Sending email for task status update: {}", taskToUpdate.getTitle());
        this.emailService.sendEmailToUpdatingTask(taskToUpdate.getTitle(), taskToUpdate.getDescription(), taskToUpdate.getStatus(), taskToUpdate.getUpdatedAt());
        return new TaskResponse(taskToUpdate.getId());
    }

    @Caching(
            evict = @CacheEvict(value = "tasks", key = "#id")
    )
    public void deleteTask(Long id) {
        logger.info("Deleting task with ID: {}", id);
        Task task = this.findTask(id);
        this.emailService.sendEmailToDeletingTask(task.getTitle(), task.getDescription(), task.getStatus(), LocalDateTime.now());
        this.repository.delete(task);
        logger.info("Task with ID: {} deleted successfully", id);
    }

    @CachePut(value = "tasks", key = "#result.id")
    private Task create(TaskRequest request){
        logger.info("Creating new task with title: {}", request.title());
        Task task = new Task(request);
        this.repository.save(task);

        logger.info("Sending email for task creation: {}", task.getTitle());

        try {
            this.emailService.sendEmailToCreatingTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedAt());
        } catch (MailAuthenticationException e) {
            logger.error("Error sending email: {}", e.getMessage());
            throw new SendMailException();
        }

        return task;
    }
}

