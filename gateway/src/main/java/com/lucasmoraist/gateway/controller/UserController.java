package com.lucasmoraist.gateway.controller;

import com.lucasmoraist.gateway.domain.dto.TokenDTO;
import com.lucasmoraist.gateway.domain.model.LoginRequest;
import com.lucasmoraist.gateway.domain.model.RegisterRequest;
import com.lucasmoraist.gateway.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("register")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        this.service.register(request);
        log.info("Registering");
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequest request) throws Exception {
        var response = this.service.login(request);
        log.info("Logging in");
        return ResponseEntity.ok().body(response);
    }

}
