package com.favor.favor.Friend;

import com.favor.favor.Friend.Account.UserFriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.Reminder.ReminderResponseDto;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public Long createFriend(FriendRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );

        Friend friend = friendRepository.save(dto.toEntity(user));
        return friend.getFriendNo();
    }

    @Transactional
    public Long addFriend(UserFriendRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );

        Friend userFriend = friendRepository.save(dto.toEntity(user));
        return userFriend.getFriendNo();
    }

    @Transactional
    public FriendResponseDto readFriend(Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        FriendResponseDto returnDto = new FriendResponseDto();

        if(friend.getIsUser() == true){
            User userFriend = userRepository.findByUserNo(friend.getUserFriendNo()).orElseThrow(
                    () -> new RuntimeException()
            );

            friend.setFriendName(userFriend.getName());

            friendRepository.save(friend);
            
            List<GiftResponseDto> g_List = new ArrayList<>();
            List<Gift> giftList = userFriend.getGiftList();
            for(Gift g : giftList){
                GiftResponseDto dto = new GiftResponseDto(g);
                g_List.add(dto);
            }

            List<ReminderResponseDto> r_List = new ArrayList<>();
            List<Reminder> reminderList = userFriend.getReminderList();
            for(Reminder r : reminderList){
                ReminderResponseDto dto = new ReminderResponseDto(r);
                r_List.add(dto);
            }

            returnDto = new FriendResponseDto(friend, g_List, r_List);
        }
        else{
            List<GiftResponseDto> g_List = new ArrayList<>();
            List<Gift> giftList = friend.getGiftList();
            for(Gift g : giftList){
                GiftResponseDto dto = new GiftResponseDto(g);
                g_List.add(dto);
            }

            List<ReminderResponseDto> r_List = new ArrayList<>();
            List<Reminder> reminderList = friend.getReminderList();
            for(Reminder r : reminderList){
                ReminderResponseDto dto = new ReminderResponseDto(r);
                r_List.add(dto);
            }
            friendRepository.save(friend);
            returnDto = new FriendResponseDto(friend, g_List, r_List);
        }

        return returnDto;
    }

    public Long updateFriend(Long friendNo, FriendUpdateRequestDto dto){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        friend.setFriendName(dto.getFriendName());
        friend.setGroup(dto.getGroup());
        friend.setFriendMemo(dto.getFriendMemo());

        friendRepository.save(friend);
        return friendNo;
    }

    public Long deleteFriend(Long friendNo){
        friendRepository.deleteById(friendNo);
        return friendNo;
    }

    public List<FriendListResponseDto> readAll(){
        List<FriendListResponseDto> f_List = new ArrayList<>();
        List<Friend> friendList = friendRepository.findAll();
        for(Friend f : friendList){
            FriendListResponseDto dto = new FriendListResponseDto(f);
            f_List.add(dto);
        }
        return f_List;
    }
}
