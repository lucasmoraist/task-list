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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse authLogin(LoginRequest dto) {
        User user = this.repository.findByEmail(dto.email())
                .orElseThrow(EmailNotFound::new);

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) throw new PasswordException();

        String token = this.tokenService.generateToken(user);

        return new LoginResponse(token);
    }

    public RegisterResponse authRegister(RegisterRequest dto) {
        Optional<User> optionalUser = this.repository.findByEmail(dto.email());
        if (optionalUser.isPresent()) throw new DuplicateException();

        User newUser = this.instanceUser(dto);

        this.repository.save(newUser);

        return new RegisterResponse(newUser.getId());
    }

    private User instanceUser(RegisterRequest dto){
        if(dto.name() == null || dto.name().isEmpty() || dto.email() == null || dto.email().isEmpty() || dto.password() == null || dto.password().isEmpty()){
            throw new IllegalArgumentException("Invalid data");
        }

        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(this.passwordEncoder.encode(dto.password()))
                .build();
    }

}
