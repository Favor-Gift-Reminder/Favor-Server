package com.favor.favor.gift;

import com.favor.favor.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByGiftNo(Long giftNo);
    List<Gift> findGiftsByUserAndGiftNameContains(User user, String giftName);
    List<Gift> findGiftsByUserAndCategory(User user, Integer categoryNo);
    List<Gift> findGiftsByUserAndEmotion(User user, Integer emotionNo);
    Boolean existsByGiftNo(Long giftNo);
}
