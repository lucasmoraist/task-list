package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.dto.TaskResponse;
import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import com.lucasmoraist.task_list.model.Task;
import com.lucasmoraist.task_list.dto.TaskRequest;
import com.lucasmoraist.task_list.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request){
        Task task = new Task(request);
        this.repository.save(task);
        return new TaskResponse(task.getId());
    }

    public List<Task> listTask(){
        return this.repository.findAll();
    }

    public Task findTask(Long id){
        return this.repository.findById(id)
                .orElseThrow(TaskNotFound::new);
    }

    public TaskResponse updateTitleTask(Long id, TaskRequest request){
        Task task = this.findTask(id);
        task.setTitle(request.title());
        this.repository.save(task);
        return new TaskResponse(task.getId());
    }

    public TaskResponse updateDescriptionTask(Long id, TaskRequest request){
        Task task = this.findTask(id);
        task.setDescription(request.description());
        this.repository.save(task);
        return new TaskResponse(task.getId());
    }

    public void deleteTask(Long id){
        Task task = this.findTask(id);
        this.repository.delete(task);
    }

}
