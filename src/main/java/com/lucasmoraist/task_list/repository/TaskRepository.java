package com.lucasmoraist.task_list.repository;

import com.lucasmoraist.task_list.model.StatusType;
import com.lucasmoraist.task_list.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatusContaining(StatusType status, Pageable pageable);
    List<Task> findTaskByStatus(@PathVariable StatusType status);
}
