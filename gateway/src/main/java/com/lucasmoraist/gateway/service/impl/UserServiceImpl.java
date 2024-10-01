package com.lucasmoraist.gateway.service.impl;

import com.lucasmoraist.gateway.domain.dto.TokenDTO;
import com.lucasmoraist.gateway.domain.entity.User;
import com.lucasmoraist.gateway.domain.model.LoginRequest;
import com.lucasmoraist.gateway.domain.model.RegisterRequest;
import com.lucasmoraist.gateway.repository.UserRepository;
import com.lucasmoraist.gateway.service.TokenService;
import com.lucasmoraist.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) throws Exception {
        log.info("Attempting to register user with email: {}", request.getEmail());

        Optional<User> optionalUser = this.repository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            log.error("Registration failed: email already in use: {}", request.getEmail());
            throw new Exception("Email already in use");
        }

        User newUser = new User(request);
        newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.repository.save(newUser);

        log.info("Registration successful for email: {}", request.getEmail());
    }

    @Override
    public TokenDTO login(LoginRequest request) throws Exception {
        log.info("Attempting to authenticate user with email: {}", request.getEmail());

        User user = this.repository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("Authentication failed: email not found for email: {}", request.getEmail());
                    return new Exception("Email not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Authentication failed: incorrect password for email: {}", request.getEmail());
            throw new Exception("Incorrect password");
        }

        String token = this.tokenService.generateToken(user);

        log.info("Authentication successful for email: {}", request.getEmail());
        return new TokenDTO(token);
    }
}
