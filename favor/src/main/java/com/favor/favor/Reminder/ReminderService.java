package com.favor.favor.Reminder;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public Long createReminder(ReminderRequestDto reminderRequestDto, Long userNo, Long friendNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend friend = friendRepository.findById(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        Reminder reminder = reminderRepository.save(reminderRequestDto.toEntity(user, friend));
        return reminder.getReminderNo();
    }

    public ReminderDetailResponseDto readReminder(Long reminderNo){
        Reminder reminder = reminderRepository.findById(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        ReminderDetailResponseDto dto = new ReminderDetailResponseDto(reminder);
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
