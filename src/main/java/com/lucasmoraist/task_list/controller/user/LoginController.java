package com.lucasmoraist.task_list.controller.user;

import com.lucasmoraist.task_list.model.dto.LoginRequest;
import com.lucasmoraist.task_list.model.dto.LoginResponse;
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
public class LoginController {

    @Autowired
    private UserService service;

    @Operation(
            summary = "Login of the user",
            description = "For user to log in to the platform"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Must return email and token to access"
    )
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest dto) {
        var response = this.service.authLogin(dto);
        log.info("Logging in");
        return ResponseEntity.ok().body(response);
    }
}
