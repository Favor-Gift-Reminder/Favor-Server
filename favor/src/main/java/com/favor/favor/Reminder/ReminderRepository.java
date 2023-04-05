package com.favor.favor.Reminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByReminderNo(Long reminderNo);
    Boolean existsByReminderNo(Long reminderNo);
}
