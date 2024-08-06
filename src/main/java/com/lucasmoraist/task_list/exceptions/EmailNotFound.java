package com.lucasmoraist.task_list.exceptions;

/**
 * Exception to be thrown when an email is not found in the database.
 *
 * @author lucasmoraist
 */
public class EmailNotFound extends RuntimeException {
    public EmailNotFound() {
        super("Email Not Found");
    }
}
