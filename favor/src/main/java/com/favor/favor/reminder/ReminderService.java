package com.favor.favor.reminder;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryRepository;
import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final AnniversaryRepository anniversaryRepository;

    @Transactional
    public Reminder createReminder(ReminderRequestDto reminderRequestDto, Long userNo, Long friendNo){
        User user = findUserByUserNo(userNo);
        Friend friend = null;
        if (friendNo != null) {
            friend = findFriendByFriendNo(friendNo);
        }
        LocalDate localDate = returnLocalDate(reminderRequestDto.getReminderDate());
        LocalDateTime localDateTime = returnLocalDateTime(reminderRequestDto.getAlarmTime());
        return reminderRepository.save(reminderRequestDto.toEntity(user, friend, localDate, localDateTime));
    }

    @Transactional
    public Reminder addReminder(Long userNo, Long anniversaryNo){
        Anniversary anniversary = findAnniversaryByAnniversaryNo(anniversaryNo);
        User user = findUserByUserNo(userNo);
        Friend friend = findFriendByFriendUserNo(anniversary.getUser().getUserNo());
        Reminder reminder = Reminder.builder()
                .reminderTitle(anniversary.getAnniversaryTitle())
                .reminderDate(anniversary.getAnniversaryDate())
                .reminderMemo("")
                .isAlarmSet(true)
                .alarmTime(null)
                .user(user)
                .friend(friend)
                .build();
        return reminderRepository.save(reminder);
    }

    @Transactional
    public void updateReminder(ReminderUpdateRequestDto dto, Long reminderNo){
        Reminder reminder = findReminderByReminderNo(reminderNo);
        Friend friend = null;
        if(dto.getFriendNo() != null){
            friend = findFriendByFriendNo(dto.getFriendNo());
        }

        reminder.setTitle(dto.getTitle());
        reminder.setReminderDate(dto.getReminderDate());
        reminder.setIsAlarmSet(dto.getIsAlarmSet());
        reminder.setAlarmTime(returnLocalDateTime(dto.getAlarmTime()));
        reminder.setReminderMemo(dto.getReminderMemo());
        reminder.setFriend(friend);

        reminderRepository.save(reminder);
    }

    @Transactional
    public void deleteReminder(Long reminderNo){
        reminderRepository.deleteById(reminderNo);
    }

    public List<ReminderSimpleDto> readAll(){
        List<ReminderSimpleDto> r_List = new ArrayList<>();
        for(Reminder r : reminderRepository.findAll()){
            Friend friend = r.getFriend();
            FriendSimpleDto friendsimpleDto = null;
            if(friend != null) {
                User friendUser = findUserByUserNo(friend.getFriendUserNo());
                UserPhoto photo = friendUser.getUserProfilePhoto();
                friendsimpleDto = new FriendSimpleDto(friend, friendUser, photo);
            }
            ReminderSimpleDto dto = new ReminderSimpleDto(r, friendsimpleDto);
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

    public Friend findFriendByFriendUserNo(Long friendUserNo){
        Friend friend = null;
        try{
            friend = friendRepository.findByFriendUserNo(friendUserNo).orElseThrow(
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

    public Anniversary findAnniversaryByAnniversaryNo(Long anniversaryNo){
        Anniversary anniversary = null;
        try{
            anniversary = anniversaryRepository.findAnniversaryByAnniversaryNo(anniversaryNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch(RuntimeException e){
            throw new CustomException(e, ANNIVERSARY_NOT_FOUND);
        }
        return anniversary;
    }



    public ReminderResponseDto returnDto(Reminder reminder){
        Friend friend = reminder.getFriend();
        FriendSimpleDto dto = null;
        if(friend != null) {
            User friendUser = findUserByUserNo(friend.getFriendUserNo());
            UserPhoto photo = friendUser.getUserProfilePhoto();
            dto = new FriendSimpleDto(friend, friendUser, photo);
        }
        return new ReminderResponseDto(reminder, dto);
    }
    public LocalDate returnLocalDate(String dateString){
        String patternDate = "yyyy-MM-dd";
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternDate);
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;

        } catch(DateTimeParseException e){
            throw new CustomException(e, DATE_INVALID);
        }
    }
    public LocalDateTime returnLocalDateTime(String dateTimeString){
        String pattern = "yyyy-MM-dd HH:mm";
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
            return dateTime;
        } catch(DateTimeParseException e){
            throw new CustomException(e, DATE_INVALID);
        }
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

    public void isExistingAnniversaryNo(Long giftNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = anniversaryRepository.existsByAnniversaryNo(giftNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, ANNIVERSARY_NOT_FOUND);
        }
    }
}
