package com.lucasmoraist.task_list.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super("Este email já existe");
    }
}
