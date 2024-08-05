package com.lucasmoraist.task_list.exceptions;

public class SendMailException extends RuntimeException{
    public SendMailException() {
        super("Error to sending email! Verify if the email is correct.");
    }
}
