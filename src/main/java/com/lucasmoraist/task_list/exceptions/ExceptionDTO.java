package com.lucasmoraist.task_list.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(String msg, HttpStatus status) {
}
