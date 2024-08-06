package com.lucasmoraist.task_list.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

/**
 * This class represents a user that can be registered in the application.
 *
 * @author lucasmoraist
 */
@Data
@Entity(name = "t_users")
@Table(name = "t_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String name;
    @Email @Column(nullable = false, unique = true, length = 180)
    private String email;
    @Column(nullable = false)
    private String password;

}
