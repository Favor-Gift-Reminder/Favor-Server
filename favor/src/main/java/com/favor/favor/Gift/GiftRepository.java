package com.favor.favor.Gift;

import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByGiftNo(Long giftNo);
    List<Gift> findGiftsByUserAndGiftName(User user, String giftName);
    List<Gift> findGiftsByUserAndCategory(User user, Integer categoryNo);
    List<Gift> findGiftsByUserAndEmotion(User user, Integer emotionNo);
    List<Gift> findGiftsByFriend(Friend friend);



}
