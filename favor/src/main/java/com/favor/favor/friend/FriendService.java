package com.favor.favor.friend;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.account.FriendUserRequestDto;
import com.favor.favor.friend.noAccount.FriendRequestDto;
import com.favor.favor.friend.noAccount.FriendUpdateRequestDto;
import com.favor.favor.gift.Gift;
import com.favor.favor.gift.GiftRepository;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;



    public Friend createFriend(FriendRequestDto dto, Long userNo){
        User user = findUserByUserNo(userNo);

        return save(dto.toEntity(user));
    }

    @Transactional
    public Friend addFriend(FriendUserRequestDto dto, Long userNo){
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
            if(f.getIsUser()) {
                if(f.getFriendUserNo() == friendUser.getUserNo()) isDuplicate = true;
            }
        }

        return isDuplicate;
    }

    @Transactional
    public void updateFriend(Friend friend, FriendUpdateRequestDto friendUpdateRequestDto){
        //회원은 변경 안돼유
        if(friend.getIsUser()) throw new CustomException(null, ILLEGAL_ARGUMENT_FRIEND);

        friend.setFriendName(friendUpdateRequestDto.getFriendName());
        friend.setFriendMemo(friendUpdateRequestDto.getFriendMemo());
        friend.setFavorList(friendUpdateRequestDto.getFavorList());
        save(friend);
    }

    @Transactional
    public void deleteFriend(Long friendNo){
        friendRepository.deleteById(friendNo);
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
    public Gift findGiftByGiftNo(Long giftNo){
        Gift gift = null;
        try{
            gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, GIFT_NOT_FOUND);
        }
        return gift;
    }
    public List<GiftResponseDto> findGiftListByFriendNo(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftResponseDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            GiftResponseDto dto = new GiftResponseDto(gift);
            giftResponseDtoList.add(dto);
        }
        return giftResponseDtoList;
    }
    public List<GiftResponseDto> findGivenGiftList(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftResponseDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(gift.getIsGiven()){
                GiftResponseDto dto = new GiftResponseDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }
    public List<GiftResponseDto> findReceivedGiftList(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftResponseDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(!gift.getIsGiven()){
                GiftResponseDto dto = new GiftResponseDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }



    //RETURN
    public FriendResponseDto returnDto(Friend friend){
        if(friend.getIsUser()) return returnDtoForFriendUser(friend);
        else return returnDtoForFriend(friend);
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
        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }
        List<Long> anniversaryNoList = new ArrayList<>();
        for(Anniversary a : user.getAnniversaryList()){
            anniversaryNoList.add(a.getAnniversaryNo());
        }
        HashMap<String, Integer> giftInfo = returnGiftInfo(friend.getFriendNo());

        return new FriendResponseDto(friend, reminderDtoList, favorList, anniversaryNoList, giftInfo);
    }
    public FriendResponseDto returnDtoForFriend(Friend friend){
        List<ReminderResponseDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : friend.getReminderList()){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            reminderDtoList.add(dto);
        }

        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : friend.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }

        List<Long> anniversaryNoList = new ArrayList<>();
        for(Long a : friend.getAnniversaryNoList()){
            anniversaryNoList.add(a);
        }
        HashMap<String, Integer> giftInfo = returnGiftInfo(friend.getFriendNo());

        return new FriendResponseDto(friend, reminderDtoList, favorList, anniversaryNoList, giftInfo);
    }
    public HashMap<String, Integer> returnGiftInfo(Long friendNo){
        HashMap<String, Integer> hashMap = new HashMap<>();
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        hashMap.put("total", giftList.size());

        int given = 0;
        int received = 0;
        for(Gift gift : giftList){
                if(gift.getIsGiven()) given++;
                else received++;
        }
        hashMap.put("given", given);
        hashMap.put("received", received);

        return hashMap;
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
