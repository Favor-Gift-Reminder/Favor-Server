package com.favor.favor.Reminder;

import com.favor.favor.Exception.CustomException;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.Exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public Reminder createReminder(ReminderRequestDto reminderRequestDto, Long userNo, Long friendNo){
        User user = findUserByUserNo(userNo);
        Friend friend = findFriendByFriendNo(friendNo);
        return reminderRepository.save(reminderRequestDto.toEntity(user, friend));
    }

    public void updateReminder(ReminderUpdateRequestDto dto, Long reminderNo, Long friendNo){
        Reminder reminder = findReminderByReminderNo(reminderNo);
        Friend friend = findFriendByFriendNo(friendNo);

        reminder.setTitle(dto.getTitle());
        reminder.setReminderDate(dto.getReminderDate());
        reminder.setIsAlarmSet(dto.getIsAlarmSet());
        reminder.setAlarmTime(dto.getAlarmTime());
        reminder.setReminderMemo(dto.getReminderMemo());
        reminder.setFriend(friend);

        reminderRepository.save(reminder);
    }

    public void deleteReminder(Long reminderNo){
        reminderRepository.deleteById(reminderNo);
    }

    public List<ReminderResponseDto> readAll(){
        List<ReminderResponseDto> r_List = new ArrayList<>();
        for(Reminder r : reminderRepository.findAll()){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_List.add(dto);
        }
        return r_List;
    }




    public User findUserByUserNo(Long userNo){
        User user = null;
        try{
            user = userRepository.findByUserNo(userNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    public Friend findFriendByFriendNo(Long friendNo){
        Friend friend = null;
        try{
            friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, FRIEND_NOT_FOUND);
        }
        return friend;
    }

    public Reminder findReminderByReminderNo(Long reminderNo){
        Reminder reminder = null;
        try{
            reminder = reminderRepository.findByReminderNo(reminderNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch(RuntimeException e){
            throw new CustomException(e, REMINDER_NOT_FOUND);
        }
        return reminder;
    }



    public ReminderResponseDto returnDto(Reminder reminder){
        return new ReminderResponseDto(reminder);
    }



    public void isExistingUserNo (Long userNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = userRepository.existsByUserNo(userNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, USER_NOT_FOUND);
        }
    }

    public void isExistingFriendNo (Long friendNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = friendRepository.existsByFriendNo(friendNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, FRIEND_NOT_FOUND);
        }
    }

    public void isExistingReminderNo(Long giftNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = reminderRepository.existsByReminderNo(giftNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, GIFT_NOT_FOUND);
        }
    }
}
