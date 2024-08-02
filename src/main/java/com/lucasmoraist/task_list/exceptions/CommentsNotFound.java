package com.lucasmoraist.task_list.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = """
        {
        	"msg": "Comments Not Found",
        	"status": "NOT_FOUND"
        }
        """)
public class CommentsNotFound extends RuntimeException{
    public CommentsNotFound(){
        super("Comments Not Found");
    }
}
