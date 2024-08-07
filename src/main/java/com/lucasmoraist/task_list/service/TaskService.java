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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for Task entity.
 * This class is responsible for handling the business logic of the application.
 * It is responsible for creating, updating, listing and deleting tasks.
 * It also sends emails to the user when a task is created, updated or deleted.
 *
 * @author lucasmoraist
 */
@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final EmailService emailService;

    public TaskService(TaskRepository repository, EmailService emailService) {
        this.emailService = emailService;
        this.repository = repository;
    }

    /**
     * Creates a new task and saves it to the database.
     *
     * @param request TaskRequest object containing the task information.
     * @return TaskResponse object containing the ID of the created task.
     */
    public TaskResponse createTask(TaskRequest request) {
        Task task = this.create(request);
        return new TaskResponse(task.getId());
    }

    /**
     * Lists all tasks in the database.
     *
     * @param sortBy Field to sort the tasks by.
     * @param order  Order to sort the tasks by.
     * @return List of tasks sorted by the specified field and order.
     */
    public List<Task> listTaskSortBy(String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Order.asc(sortBy)) :
                Sort.by(Sort.Order.desc(sortBy));
        return this.repository.findAll(sort);
    }

    /**
     * Lists all tasks in the database with a specific status.
     *
     * @param status   Status to filter the tasks by.
     * @param pageable Pageable object containing the page number and size.
     * @return Page object containing the tasks with the specified status.
     */
    public Page<EntityModel<Task>> listTaskPage(StatusType status, Pageable pageable) {
        logger.info("Listing tasks with status: {}", status);
        Page<Task> tasks = (status == null) ?
                this.repository.findAll(pageable) :
                this.repository.findByStatusContaining(status, pageable);

        return tasks.map(Task::toEntityModel);
    }

    /**
     * Finds a task in the database by its ID.
     *
     * @param id ID of the task to be found.
     * @return Task object with the specified ID.
     * @throws TaskNotFound if the task with the specified ID is not found.
     */
    public Task findTask(Long id) {
        logger.info("Finding task with ID: {} from database", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Task with ID: {} not found", id);
                    return new TaskNotFound();
                });
    }

    /**
     * Finds all tasks in the database with a specific status.
     *
     * @param status Status to filter the tasks by.
     * @return List of tasks with the specified status.
     */
    public List<Task> findTasksByStatus(StatusType status) {
        logger.info("Finding tasks with status: {}", status);
        return this.repository.findTaskByStatus(status);
    }

    /**
     * Updates the title of a task in the database.
     *
     * @param id      ID of the task to be updated.
     * @param request TaskRequest object containing the new title.
     * @return TaskResponse object containing the ID of the updated task.
     * @throws TaskNotFound if the task with the specified ID is not found.
     */
    public TaskResponse updateTitleTask(Long id, TaskRequest request) {
        Task task = this.findTask(id);
        return this.update("title", task, request);
    }

    /**
     * Updates the description of a task in the database.
     *
     * @param id      ID of the task to be updated.
     * @param request TaskRequest object containing the new description.
     * @return TaskResponse object containing the ID of the updated task.
     * @throws TaskNotFound if the task with the specified ID is not found.
     */
    public TaskResponse updateDescriptionTask(Long id, TaskRequest request) {
        Task task = this.findTask(id);
        return this.update("description", task, request);
    }

    /**
     * Updates the status of a task in the database.
     *
     * @param id      ID of the task to be updated.
     * @param request TaskRequest object containing the new status.
     * @return TaskResponse object containing the ID of the updated task.
     * @throws TaskNotFound if the task with the specified ID is not found.
     */
    public TaskResponse updateStatusTask(Long id, TaskRequest request) {
        Task task = this.findTask(id);
        return this.update("status", task, request);
    }

    /**
     * Deletes a task from the database.
     *
     * @param id ID of the task to be deleted.
     * @throws TaskNotFound if the task with the specified ID is not found.
     */
    public void deleteTask(Long id) {
        logger.info("Deleting task with ID: {}", id);
        Task task = this.findTask(id);
        this.emailService.sendEmail("deleting", task, LocalDateTime.now());
        this.repository.delete(task);
        logger.info("Task with ID: {} deleted successfully", id);
    }

    private TaskResponse update(String update, Task task, TaskRequest request) {
        switch (update) {
            case "title":
                logger.info("Updating title of task with ID: {}", task.getId());
                task.setTitle(request.title());
                this.repository.save(task);

                logger.info("Sending email for task title update: {}", task.getTitle());
                break;
            case "description":
                logger.info("Updating description of task with ID: {}", task.getId());
                task.setDescription(request.description());
                this.repository.save(task);

                logger.info("Sending email for task description update: {}", task.getTitle());
                break;
            case "status":
                logger.info("Updating status of task with ID: {}", task.getId());
                task.setStatus(request.status());
                this.repository.save(task);

                logger.info("Sending email for task status update: {}", task.getTitle());
                break;
        }
        this.emailService.sendEmail("updating", task, task.getUpdatedAt());
        return new TaskResponse(task.getId());
    }

    private Task create(TaskRequest request) {
        logger.info("Creating new task with title: {}", request.title());
        Task task = new Task(request);
        this.repository.save(task);

        logger.info("Sending email for task creation: {}", task.getTitle());

        try {
            this.emailService.sendEmail("creating", task, task.getCreatedAt());
        } catch (MailAuthenticationException e) {
            logger.error("Error sending email: {}", e.getMessage());
            throw new SendMailException();
        }

        return task;
    }
}

