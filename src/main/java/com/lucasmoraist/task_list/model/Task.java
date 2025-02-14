package com.lucasmoraist.task_list.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lucasmoraist.task_list.controller.task.ListTaskController;
import com.lucasmoraist.task_list.model.dto.TaskRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * This class represents a task that can be added to a task list.
 *
 * @see Comment
 * @author lucasmoraist
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_task")
@Table(name = "t_task")
@Builder
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80)
    private String title;
    @Column(nullable = false, length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    private List<Comment> comment;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Task(TaskRequest request){
        this.title = request.title();
        this.description = request.description();
        this.status = StatusType.PENDING;
    }

    public EntityModel<Task> toEntityModel(){
        return EntityModel.of(
                this,
                linkTo(methodOn(ListTaskController.class).listTask(null, Pageable.unpaged())).withRel("all")
                );
    }
}
