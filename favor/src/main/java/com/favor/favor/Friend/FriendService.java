package com.favor.favor.Friend;

import com.favor.favor.Enum.Favor;
import com.favor.favor.Friend.Account.UserFriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftRepository;
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
    private final GiftRepository giftRepository;

    public FriendDetailResponseDto createFriend(FriendRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );

        Friend friend = friendRepository.save(dto.toEntity(user));
        return new FriendDetailResponseDto(friend);
    }

    @Transactional
    public FriendDetailResponseDto addFriend(UserFriendRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Long userFriendNo = dto.getFriendUserNo();
        User friendUser = userRepository.findByUserNo(userFriendNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend userFriend = friendRepository.save(dto.toEntity(user, friendUser));

        return new FriendDetailResponseDto(userFriend);
    }

    @Transactional
    public FriendDetailResponseDto readFriend(Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        FriendDetailResponseDto returnDto = new FriendDetailResponseDto();

        if(friend.getIsUser()){
            User user = userRepository.findByUserNo(friend.getFriendUserNo()).orElseThrow(
                    () -> new RuntimeException()
            );

            friend.setFriendName(user.getName());

            List<Reminder> reminderList = friend.getReminderList();
            for(Reminder r : user.getReminderList()){
                reminderList.add(r);
            }

            List<ReminderResponseDto> r_List = new ArrayList<>();
            for(Reminder r : reminderList){
                ReminderResponseDto dto = new ReminderResponseDto(r);
                r_List.add(dto);
            }

            List<Favor> favor_List = new ArrayList<>();
            for(Integer favorType : user.getFavorList()){
                favor_List.add(Favor.valueOf(favorType));
            }

            friendRepository.save(friend);

            returnDto = new FriendDetailResponseDto(friend, r_List, favor_List);
        }
        else{
            List<ReminderResponseDto> r_List = new ArrayList<>();
            List<Reminder> reminderList = friend.getReminderList();
            for(Reminder r : reminderList){
                ReminderResponseDto dto = new ReminderResponseDto(r);
                r_List.add(dto);
            }
            friendRepository.save(friend);

            returnDto = new FriendDetailResponseDto(friend, r_List);
        }

        return returnDto;
    }

    @Transactional
    public FriendDetailResponseDto updateFriend(Long friendNo, FriendUpdateRequestDto friendUpdateRequestDto){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

        if(friend.getIsUser()) throw new RuntimeException(); //회원은 변경 안돼유

        friend.setFriendName(friendUpdateRequestDto.getFriendName());
        friend.setFriendMemo(friendUpdateRequestDto.getFriendMemo());

        friendRepository.save(friend);

        FriendDetailResponseDto returnDto = new FriendDetailResponseDto();

        List<ReminderResponseDto> r_List = new ArrayList<>();
        List<Reminder> reminderList = friend.getReminderList();
        for(Reminder r : reminderList){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_List.add(dto);
        }
        returnDto = new FriendDetailResponseDto(friend, r_List);

        return returnDto;
    }

    @Transactional
    public FriendDetailResponseDto deleteFriend(Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

        FriendDetailResponseDto returnDto = new FriendDetailResponseDto();

        if(friend.getIsUser()){
            User user = userRepository.findByUserNo(friend.getFriendUserNo()).orElseThrow(
                    () -> new RuntimeException()
            );

            friend.setFriendName(user.getName());

            List<Reminder> reminderList = friend.getReminderList();
            for(Reminder r : user.getReminderList()){
                reminderList.add(r);
            }

            List<ReminderResponseDto> r_List = new ArrayList<>();
            for(Reminder r : reminderList){
                ReminderResponseDto dto = new ReminderResponseDto(r);
                r_List.add(dto);
            }
            List<Favor> favor_List = new ArrayList<>();
            for(Integer favorType : user.getFavorList()){
                favor_List.add(Favor.valueOf(favorType));
            }

            returnDto = new FriendDetailResponseDto(friend, r_List, favor_List);
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
            returnDto = new FriendDetailResponseDto(friend, r_List);
        }

        List<Gift> giftList = giftRepository.findGiftsByFriend(friend);
        for(Gift gift : giftList){
            gift.setFriend(null);
        }

        friendRepository.deleteById(friendNo);

        return returnDto;
    }

    public List<FriendResponseDto> readAll(){
        List<FriendResponseDto> f_List = new ArrayList<>();
        List<Friend> friendList = friendRepository.findAll();
        for(Friend f : friendList){
            FriendResponseDto dto = new FriendResponseDto(f);
            f_List.add(dto);
        }
        return f_List;
    }
}
