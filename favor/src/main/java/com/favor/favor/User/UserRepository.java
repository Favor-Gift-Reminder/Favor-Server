package com.favor.favor.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNo(Long userNo);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByEmail(String email);
    void deleteByUserNo(Long userNo);
    Boolean existsByEmail(String email);
    Boolean existsByUserId(String id);
    Boolean existsByUserNo(Long userNo);
}
