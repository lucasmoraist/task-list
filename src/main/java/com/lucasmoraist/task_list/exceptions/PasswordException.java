package com.lucasmoraist.task_list.exceptions;

/**
 * Exception to be thrown when the password is incorrect.
 *
 * @author lucasmoraist
 */
public class PasswordException extends RuntimeException{
    public PasswordException() {
        super("Password Incorret");
    }
}
