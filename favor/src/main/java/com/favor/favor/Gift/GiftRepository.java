package com.favor.favor.Gift;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByGiftNo(Long giftNo);

    List<Gift> findGiftsByGiftNameAndUser(Long userNo, String giftName);

    Optional<Gift> findByCategoryAndUser(Long userNo, String category);
}
