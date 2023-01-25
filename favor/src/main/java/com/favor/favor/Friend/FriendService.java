package com.favor.favor.Friend;

import com.favor.favor.Friend.Account.UserFriendResponseDto;
import com.favor.favor.Friend.NoAccount.FriendResponseDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftRepository;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.Reminder.ReminderRepository;
import com.favor.favor.Reminder.ReminderResponseDto;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ReminderRepository reminderRepository;
    private final GiftRepository giftRepository;

    public Long createFriend(FriendRequestDto dto){
        Friend friend = friendRepository.save(dto.toEntity());
        return friend.getFriendNo();
    }

    public FriendResponseDto readFriend(Long friendNo){
        Friend friend = friendRepository.findById(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

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

        FriendResponseDto dto = new FriendResponseDto(friend, g_List, r_List);
        return dto;
    }

    public UserFriendResponseDto readUserFriend(Long friendNo){
        Friend friend = friendRepository.findById(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

        User user = friend.getUser();

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

        UserFriendResponseDto dto = new UserFriendResponseDto(user, friend, g_List, r_List);
        return dto;
    }

    public Long updateFriend(Long friendNo, FriendUpdateRequestDto dto){
        Friend friend = friendRepository.findById(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        friend.setFriendName(dto.getFriendName());
        friend.setGroup(dto.getGroup());
        friend.setFriendMemo(dto.getFriendMemo());
        return friendNo;
    }

    public Long deleteFriend(Long friendNo){
        friendRepository.deleteById(friendNo);
        return friendNo;
    }
}
