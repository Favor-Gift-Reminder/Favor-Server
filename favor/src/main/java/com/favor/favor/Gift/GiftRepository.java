package com.favor.favor.Gift;

import com.favor.favor.Common.Category;
import com.favor.favor.Common.Emotion;
import com.favor.favor.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByGiftNo(Long giftNo);

    List<Gift> findGiftsByUserAndGiftName(User user, String giftName);

    List<Gift> findGiftsByUserAndCategory(User user, Category category);

    List<Gift> findGiftsByUserAndEmotion(User user, Emotion emotion);
}
