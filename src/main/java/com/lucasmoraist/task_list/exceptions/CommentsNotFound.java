package com.lucasmoraist.task_list.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Exception to be thrown when no comments are found in the database.
 *
 * @author lucasmoraist
 */
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
