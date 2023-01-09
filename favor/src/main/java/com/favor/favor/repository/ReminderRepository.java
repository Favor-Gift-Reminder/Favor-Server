package com.favor.favor.repository;

import com.favor.favor.domain.Gift;
import com.favor.favor.domain.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
