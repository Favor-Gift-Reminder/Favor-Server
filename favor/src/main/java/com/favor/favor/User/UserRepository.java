package com.favor.favor.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNo(Long userNo);
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    void deleteByUserNo(Long userNo);
    Boolean existsByEmail(String email);
    Boolean existsByUserId(String id);
    Boolean existsByUserNo(Long userNo);

}
