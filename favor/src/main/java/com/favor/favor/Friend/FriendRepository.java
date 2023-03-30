package com.favor.favor.Friend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFriendNo(Long friendNo);
    void deleteFriendsByFriendUserNo(Long userNo);
//    Optional<Friend> findFriendByGiftNoListContains(Long giftNo);
    Boolean existsByFriendNo(Long frinedNo);
}
