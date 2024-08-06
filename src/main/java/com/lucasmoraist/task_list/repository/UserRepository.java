package com.lucasmoraist.task_list.repository;

import com.lucasmoraist.task_list.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to manage persistence operations for the entity {@link User}.
 *
 * @author lucasmoraist
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
