package com.lucasmoraist.task_list.repository;

import com.lucasmoraist.task_list.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage persistence operations for the entity {@link Comment}.
 *
 * @author lucasmoraist
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
