package com.favor.favor.Reminder;

import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public void createReminder(ReminderRequestDto reminderRequestDto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        reminderRepository.save(reminderRequestDto.toEntity(user));
    }

    public ReminderResponseDto readReminder(Long reminderNo){
        Reminder reminder = reminderRepository.findById(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        ReminderResponseDto dto = new ReminderResponseDto(reminder);
        return dto;
    }

    public Long updateReminder(Long reminderNo, ReminderUpdateRequestDto dto){
        Reminder reminder = reminderRepository.findById(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        reminder.setTitle(dto.getTitle());
        reminder.setReminderDate(dto.getReminderDate());
        reminder.setIsAlarmSet(dto.getIsAlarmSet());
        reminder.setAlarmTime(dto.getAlarmTime());
        reminder.setReminderMemo(dto.getMemo());

        reminderRepository.save(reminder);
        return reminderNo;
    }

    public Long deleteReminder(Long reminderNo){
        reminderRepository.deleteById(reminderNo);
        return reminderNo;
    }
}
