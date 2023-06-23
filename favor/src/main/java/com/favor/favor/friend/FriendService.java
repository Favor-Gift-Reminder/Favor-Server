package com.favor.favor.friend;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.exception.CustomException;
import com.favor.favor.gift.Gift;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;




    @Transactional
    public Friend addFriend(FriendRequestDto dto, Long userNo){
        User user = findUserByUserNo(userNo);
        Long friendUserNo = dto.getFriendUserNo();
        User friendUser = findUserByUserNo(friendUserNo);

        if(isDuplicateFriendUser(user, friendUser))  throw new CustomException(null, DUPLICATE_FRIEND);

        return save(dto.toEntity(user, friendUser));
    }

    public Boolean isDuplicateFriendUser(User user, User friendUser){
        Boolean isDuplicate = false;
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            if(f.getFriendUserNo() == friendUser.getUserNo()) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate;
    }

    @Transactional
    public void deleteFriend(Long friendNo){
        friendRepository.deleteById(friendNo);
    }

    @Transactional
    public List<FriendResponseDto> readAll(){
        List<FriendResponseDto> f_List = new ArrayList<>();
        List<Friend> friendList = friendRepository.findAll();
        for(Friend f : friendList) f_List.add(returnDto(f));
        return f_List;
    }


    @Transactional
    public Friend save(Friend friend){
        try{
            return friendRepository.save(friend);
        }catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }


    //FIND
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



    //RETURN
    @Transactional
    public FriendResponseDto returnDto(Friend friend){
        User user = userRepository.findByUserNo(friend.getFriendUserNo()).orElseThrow(
                () -> new RuntimeException()
        );

        List<Reminder> reminderList = user.getReminderList();
        List<ReminderResponseDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : reminderList){
            reminderDtoList.add(new ReminderResponseDto(r));
        }
        List<GiftResponseDto> giftDtoList = new ArrayList<>();
        for(Gift g : user.getGiftList()){
            giftDtoList.add(new GiftResponseDto(g, null));
        }
        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }
        List<Long> anniversaryNoList = new ArrayList<>();
        for(Anniversary a : user.getAnniversaryList()){
            anniversaryNoList.add(a.getAnniversaryNo());
        }

        return new FriendResponseDto(friend, reminderDtoList, giftDtoList, favorList, anniversaryNoList);
    }



    //IS_EXISTING
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

    public void isExistingFriendUserNo (Long userNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = userRepository.existsByUserNo(userNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, FRIEND_USER_NOT_FOUND);
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
}
