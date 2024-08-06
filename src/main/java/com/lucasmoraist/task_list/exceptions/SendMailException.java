package com.lucasmoraist.task_list.exceptions;

/**
 * Exception to be thrown when an error occurs while sending an email.
 *
 * @author lucasmoraist
 */
public class SendMailException extends RuntimeException{
    public SendMailException() {
        super("Error to sending email! Verify if the email is correct.");
    }
}
