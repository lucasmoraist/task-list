package com.lucasmoraist.task_list.exceptions;

public class EmailNotFound extends RuntimeException {
    public EmailNotFound() {
        super("Email Not Found");
    }
}
