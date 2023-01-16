package com.favor.favor.Repository;

import com.favor.favor.Domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {

}
