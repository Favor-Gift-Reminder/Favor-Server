package com.favor.favor.repository;

import com.favor.favor.domain.Friend;
import com.favor.favor.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
