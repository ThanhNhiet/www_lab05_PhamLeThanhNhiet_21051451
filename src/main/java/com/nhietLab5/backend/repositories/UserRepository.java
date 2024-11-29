package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Page<User> findAll(Pageable pageable);
    Page<User> findAllByIdNot(Long adminId, Pageable pageable);
}