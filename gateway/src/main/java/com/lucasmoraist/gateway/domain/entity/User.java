package com.lucasmoraist.gateway.domain.entity;

import com.lucasmoraist.gateway.domain.model.LoginRequest;
import com.lucasmoraist.gateway.domain.model.RegisterRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_user")
@Table(name = "t_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    public User(RegisterRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }

    public User(LoginRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
    }

}
