package com.lucasmoraist.task_list.model;

/**
 * This enum represents the status of a task.
 *
 * @see Task
 */
public enum StatusType {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String status;

    StatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
