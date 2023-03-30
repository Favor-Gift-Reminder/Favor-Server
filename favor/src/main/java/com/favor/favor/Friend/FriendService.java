package com.favor.favor.Friend;

import com.favor.favor.Enum.Favor;
import com.favor.favor.Friend.Account.FriendUserRequestDto;
import com.favor.favor.Friend.NoAccount.FriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftRepository;
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

    public FriendResponseDto createFriend(FriendRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );

        Friend friend = friendRepository.save(dto.toEntity(user));
        return new FriendResponseDto(friend);
    }

    @Transactional
    public FriendResponseDto addFriend(FriendUserRequestDto dto, Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Long friendUserNo = dto.getFriendUserNo();
        User friendUser = userRepository.findByUserNo(friendUserNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend userFriend = friendRepository.save(dto.toEntity(user, friendUser));

        return returnDtoForFriendUser(userFriend);
    }

    @Transactional
    public FriendResponseDto readFriend(Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        FriendResponseDto returnDto;

        if(friend.getIsUser()){ returnDto = returnDtoForFriendUser(friend); }
        else{ returnDto = returnDtoForFriend(friend); }

        return returnDto;
    }

    @Transactional
    public FriendResponseDto updateFriend(Long friendNo, FriendUpdateRequestDto friendUpdateRequestDto){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

        if(friend.getIsUser()) throw new RuntimeException(); //회원은 변경 안돼유

        friend.setFriendName(friendUpdateRequestDto.getFriendName());
        friend.setFriendMemo(friendUpdateRequestDto.getFriendMemo());

        friendRepository.save(friend);

        return returnDtoForFriend(friend);
    }

    @Transactional
    public FriendResponseDto deleteFriend(Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );

        FriendResponseDto returnDto = new FriendResponseDto();

        if(friend.getIsUser()){ returnDto = returnDtoForFriendUser(friend); }
        else{ returnDto = returnDtoForFriend(friend); }

        friendRepository.deleteById(friendNo);

        return returnDto;
    }

    @Transactional
    public List<FriendResponseDto> readAll(){
        List<FriendResponseDto> f_List = new ArrayList<>();
        List<Friend> friendList = friendRepository.findAll();
        for(Friend f : friendList){
            FriendResponseDto dto;
            if(f.getIsUser()){ dto = returnDtoForFriendUser(f); }
            else{ dto = returnDtoForFriend(f); }
            f_List.add(dto);
        }
        return f_List;
    }

    public FriendResponseDto returnDtoForFriendUser(Friend friend){
        User user = userRepository.findByUserNo(friend.getFriendUserNo()).orElseThrow(
                () -> new RuntimeException()
        );

        List<Reminder> reminderList = user.getReminderList();
        List<ReminderResponseDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : reminderList){
            reminderDtoList.add(new ReminderResponseDto(r));
        }
//        List<GiftDetailResponseDto> giftDtoList = new ArrayList<>();
//        for(Gift g : user.getGiftList()){
//            giftDtoList.add(new GiftDetailResponseDto(g));
//        }
        List<Long> giftNoList = new ArrayList<>();
        for(Gift g : user.getGiftList()){
            giftNoList.add(g.getGiftNo());
        }
        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }

        friendRepository.save(friend);

        return new FriendResponseDto(friend, reminderDtoList, giftNoList, favorList);
    }
    public FriendResponseDto returnDtoForFriend(Friend friend){
        List<ReminderResponseDto> reminderDtoList = new ArrayList<>();
        List<Reminder> reminderList = friend.getReminderList();
        for(Reminder r : reminderList){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            reminderDtoList.add(dto);
        }
//        List<GiftDetailResponseDto> giftList = new ArrayList<>();
//        for(Long g : friend.getGiftNoList()){
//            Gift gift = giftRepository.findByGiftNo(g).orElseThrow(
//                    () -> new RuntimeException()
//            );
//            GiftDetailResponseDto dto = new GiftDetailResponseDto(gift);
//            giftList.add(dto);
//        }
        List<Long> giftNoList = new ArrayList<>();
        for(Long g : friend.getGiftNoList()){
            giftNoList.add(g);
        }
        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : friend.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }
        friendRepository.save(friend);

        return new FriendResponseDto(friend, reminderDtoList, giftNoList, favorList);
    }
}
