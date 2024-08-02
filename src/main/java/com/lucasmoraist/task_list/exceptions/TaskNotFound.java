package com.lucasmoraist.task_list.exceptions;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound(){
        super("Task Not Found");
    }
}
