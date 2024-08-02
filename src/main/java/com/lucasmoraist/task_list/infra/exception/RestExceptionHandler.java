package com.lucasmoraist.task_list.infra.exception;

import com.lucasmoraist.task_list.exceptions.ExceptionDTO;
import com.lucasmoraist.task_list.exceptions.TaskNotFound;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFound.class)
    protected ResponseEntity<ExceptionDTO> resourceTaskNotFound(TaskNotFound e){
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ExceptionDTO> dataIntegrityViolation(){
        return ResponseEntity.badRequest().body(
                new ExceptionDTO("Fill in all mandatory fields", HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> genericException(Exception e){
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST)
        );
    }

}
