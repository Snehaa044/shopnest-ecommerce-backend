package com.sneha.shopnest.repository;

import com.sneha.shopnest.entity.User;
import com.sneha.shopnest.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByRole(Role role);
}