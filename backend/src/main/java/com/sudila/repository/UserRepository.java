package com.sudila.repository;

import com.sudila.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository interface for User entity, providing CRUD operations
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by their email address
    User findByEmail(String email);
}
