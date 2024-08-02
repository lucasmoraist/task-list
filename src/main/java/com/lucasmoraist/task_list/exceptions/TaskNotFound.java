package com.lucasmoraist.task_list.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = """
        {
        	"msg": "Task Not Found",
        	"status": "NOT_FOUND"
        }
        """)
public class TaskNotFound extends RuntimeException {
    public TaskNotFound(){
        super("Task Not Found");
    }
}
