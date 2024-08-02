package com.lucasmoraist.task_list.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_comment")
@Entity(name = "t_comment")
public class Comment {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String text;

        @ManyToOne
        @JoinColumn(name = "task_id")
        private Task task;
}
