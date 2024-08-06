package com.lucasmoraist.task_list.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a comment that can be added to a task.
 *
 * @see Task
 * @author lucasmoraist
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_comment")
@Entity(name = "t_comment")
@Builder
public class Comment {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String text;

        @ManyToOne
        @JoinColumn(name = "task_id")
        @JsonBackReference
        private Task task;
}
