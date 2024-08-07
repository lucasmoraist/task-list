package com.lucasmoraist.task_list.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    private User user;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("encoded-password")
                .build();
        registerRequest = new RegisterRequest("Test User", "test@example.com", "password");
        loginRequest = new LoginRequest("test@example.com", "password");
    }

    @Test
    @DisplayName("authLoginIsSuccess: Deve autenticar um usuário com sucesso")
    void case01() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenService.generateToken(any(User.class))).thenReturn("dummy-token");

        LoginResponse response = userService.authLogin(loginRequest);

        assertNotNull(response);
        assertEquals("dummy-token", response.token());
        verify(repository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(tokenService, times(1)).generateToken(any(User.class));
    }

    @Test
    @DisplayName("authLoginIsNotSuccess: Deve lançar exceção ao autenticar com email não encontrado")
    void case02() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(EmailNotFound.class, () -> userService.authLogin(loginRequest));
    }

    @Test
    @DisplayName("authLoginIsNotSuccess: Deve lançar exceção ao autenticar com senha incorreta")
    void case03() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(PasswordException.class, () -> userService.authLogin(loginRequest));
    }

    @Test
    @DisplayName("authRegisterIsSuccess: Deve registrar um novo usuário com sucesso")
    void case04() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        RegisterResponse response = userService.authRegister(registerRequest);

        assertNotNull(response);
        assertEquals(user.getId(), response.idUser());
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("authRegisterIsNotSuccess: Deve lançar exceção ao registrar com email já em uso")
    void case05() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(DuplicateException.class, () -> userService.authRegister(registerRequest));
    }

}