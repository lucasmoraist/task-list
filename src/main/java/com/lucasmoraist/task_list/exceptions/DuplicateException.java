package com.lucasmoraist.task_list.exceptions;

/**
 * Exception to be thrown when a duplicate email is found in the database.
 *
 * @author lucasmoraist
 */
public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super("Este email já existe");
    }
}
