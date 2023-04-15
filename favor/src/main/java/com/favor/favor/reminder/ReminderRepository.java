package com.favor.favor.reminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByReminderNo(Long reminderNo);
    Boolean existsByReminderNo(Long reminderNo);
    List<Reminder> findAllByReminderDateBetween(LocalDate start, LocalDate end);
}
