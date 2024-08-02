package com.lucasmoraist.task_list.repository;

import com.lucasmoraist.task_list.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTitleContaining(String title, Pageable pageable);
}
