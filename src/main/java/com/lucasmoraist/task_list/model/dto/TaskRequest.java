package com.lucasmoraist.task_list.model.dto;

import com.lucasmoraist.task_list.model.StatusType;

public record TaskRequest(String title, String description, StatusType status) {
}
