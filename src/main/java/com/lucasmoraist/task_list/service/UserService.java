package com.lucasmoraist.task_list.service;

import com.lucasmoraist.task_list.exceptions.DuplicateException;
import com.lucasmoraist.task_list.exceptions.EmailNotFound;
import com.lucasmoraist.task_list.exceptions.PasswordException;
import com.lucasmoraist.task_list.infra.security.TokenService;
import com.lucasmoraist.task_list.model.User;
import com.lucasmoraist.task_list.model.dto.LoginRequest;
import com.lucasmoraist.task_list.model.dto.LoginResponse;
import com.lucasmoraist.task_list.model.dto.RegisterRequest;
import com.lucasmoraist.task_list.model.dto.RegisterResponse;
import com.lucasmoraist.task_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse authLogin(LoginRequest dto) {
        logger.info("Attempting to authenticate user with email: {}", dto.email());

        User user = this.repository.findByEmail(dto.email())
                .orElseThrow(() -> {
                    logger.error("Authentication failed: email not found for email: {}", dto.email());
                    return new EmailNotFound();
                });

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            logger.error("Authentication failed: incorrect password for email: {}", dto.email());
            throw new PasswordException();
        }

        String token = this.tokenService.generateToken(user);

        logger.info("Authentication successful for email: {}", dto.email());
        return new LoginResponse(token);
    }

    public RegisterResponse authRegister(RegisterRequest dto) {
        logger.info("Attempting to register user with email: {}", dto.email());

        Optional<User> optionalUser = this.repository.findByEmail(dto.email());
        if (optionalUser.isPresent()) {
            logger.error("Registration failed: email already in use: {}", dto.email());
            throw new DuplicateException();
        }

        User newUser = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(this.passwordEncoder.encode(dto.password()))
                .build();

        this.repository.save(newUser);

        logger.info("Registration successful for email: {}", dto.email());
        return new RegisterResponse(newUser.getId());
    }

}
