package com.lucasmoraist.task_list.controller.user;

import com.lucasmoraist.task_list.model.dto.RegisterRequest;
import com.lucasmoraist.task_list.model.dto.RegisterResponse;
import com.lucasmoraist.task_list.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "User")
public class RegisterController {

    @Autowired
    private UserService service;

    @Operation(
            summary = "Register new user",
            description = "Must create a user to access freely"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Must return email and token to access"
    )
    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest dto) {
        var response = this.service.authRegister(dto);
        log.info("Registering");
        return ResponseEntity.ok().body(response);
    }
}
