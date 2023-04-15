package com.favor.favor.friend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFriendNo(Long friendNo);
    Optional<Friend> findByFriendUserNo(Long friendUserNo);
    void deleteFriendsByFriendUserNo(Long userNo);
    Boolean existsByFriendNo(Long frinedNo);
}
