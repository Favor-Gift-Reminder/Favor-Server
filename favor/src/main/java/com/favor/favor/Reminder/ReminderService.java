package com.favor.favor.Reminder;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public ReminderDetailResponseDto createReminder(ReminderRequestDto reminderRequestDto, Long userNo, Long friendNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        Reminder reminder = reminderRepository.save(reminderRequestDto.toEntity(user, friend));
        return new ReminderDetailResponseDto(reminder);
    }

    public ReminderDetailResponseDto readReminder(Long reminderNo){
        Reminder reminder = reminderRepository.findByReminderNo(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        ReminderDetailResponseDto dto = new ReminderDetailResponseDto(reminder);
        return dto;
    }

    public ReminderDetailResponseDto updateReminder(Long reminderNo, ReminderUpdateRequestDto dto, Long friendNo){
        Reminder reminder = reminderRepository.findByReminderNo(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        reminder.setTitle(dto.getTitle());
        reminder.setReminderDate(dto.getReminderDate());
        reminder.setIsAlarmSet(dto.getIsAlarmSet());
        reminder.setAlarmTime(dto.getAlarmTime());
        reminder.setReminderMemo(dto.getReminderMemo());
        reminder.setFriend(friend);

        reminderRepository.save(reminder);


        return new ReminderDetailResponseDto(reminder);
    }

    public ReminderDetailResponseDto deleteReminder(Long reminderNo){
        Reminder reminder = reminderRepository.findByReminderNo(reminderNo).orElseThrow(
                () -> new RuntimeException()
        );
        ReminderDetailResponseDto returnDto = new ReminderDetailResponseDto(reminder);
        reminderRepository.deleteById(reminderNo);
        return returnDto;
    }

    public List<ReminderDetailResponseDto> readAll(){
        List<ReminderDetailResponseDto> r_List = new ArrayList<>();
        for(Reminder r : reminderRepository.findAll()){
            ReminderDetailResponseDto dto = new ReminderDetailResponseDto(r);
            r_List.add(dto);
        }
        return r_List;
    }
}
