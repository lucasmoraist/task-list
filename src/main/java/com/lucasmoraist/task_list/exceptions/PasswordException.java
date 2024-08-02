package com.lucasmoraist.task_list.exceptions;

public class PasswordException extends RuntimeException{
    public PasswordException() {
        super("Password Incorret");
    }
}
