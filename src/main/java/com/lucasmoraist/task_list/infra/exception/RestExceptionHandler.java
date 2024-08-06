package com.lucasmoraist.task_list.infra.exception;

import com.lucasmoraist.task_list.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFound.class)
    protected ResponseEntity<ExceptionDTO> resourceTaskNotFound(TaskNotFound e) {
        log.error("TaskNotFound exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ExceptionDTO> dataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO("Fill in all mandatory fields", HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(EmailNotFound.class)
    protected ResponseEntity<ExceptionDTO> emailNotFound(EmailNotFound e) {
        log.error("EmailNotFound exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<ExceptionDTO> duplicateException(DuplicateException e) {
        log.error("DuplicateException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(PasswordException.class)
    protected ResponseEntity<ExceptionDTO> passwordException(PasswordException e) {
        log.error("PasswordException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(MailAuthenticationException.class)
    protected ResponseEntity<ExceptionDTO> sendMailException(MailAuthenticationException e) {
        log.error("MailAuthenticationException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ExceptionDTO> usernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> genericException(Exception e) {
        log.error("Generic exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST)
        );
    }

}
