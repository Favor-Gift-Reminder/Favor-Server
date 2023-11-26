package com.favor.favor.friend;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.exception.CustomException;
import com.favor.favor.gift.Gift;
import com.favor.favor.gift.GiftRepository;
import com.favor.favor.gift.GiftSimpleDto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.Reminder;
import org.springframework.transaction.annotation.Transactional;
import com.favor.favor.reminder.ReminderSimpleDto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;


    @Transactional
    public Friend addFriend(FriendRequestDto dto, Long userNo){
        User user = findUserByUserNo(userNo);
        Long friendUserNo = dto.getFriendUserNo();
        User friendUser = findUserByUserNo(friendUserNo);

        if(isDuplicateFriendUser(user, friendUser)) {
            throw new CustomException(null, DUPLICATE_FRIEND);
        }
        return save(dto.toEntity(user, friendUser));
    }

    public Boolean isDuplicateFriendUser(User user, User friendUser){
        Boolean isDuplicate = false;
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            if(f.getFriendUserNo() == friendUser.getUserNo() || f.getFriendUserNo() == user.getUserNo()) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate;
    }

    @Transactional
    public void updateMemo(Friend friend, MemoUpdateRequestDto memoUpdateRequestDto){
        friend.setFriendMemo(memoUpdateRequestDto.getMemo());
        friendRepository.save(friend);

    }

    //친구 삭제
    @Transactional
    public void deleteFriend(Long friendNo){
        Friend friend = findFriendByFriendNo(friendNo);

        //친구의 giftNoList
        List<Long> giftNoList = friend.getGiftNoList();
        //선물들의 친구 목록에서 친구 삭제
        for(Long g : giftNoList){
            Gift gift = findGiftByGiftNo(g);
            gift.removeFriendNo(friendNo);
            giftRepository.save(gift);
        }
        friendRepository.deleteById(friendNo);
    }

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
    public Gift findGiftByGiftNo(Long giftNo){
        Gift gift = null;
        try{
            gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                    () -> new RuntimeException()
            );
        }catch(RuntimeException e){
            throw new CustomException(e, GIFT_NOT_FOUND);
        }

        return gift;
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
    public List<GiftSimpleDto> findGiftListByFriendNo(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftSimpleDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            GiftSimpleDto dto = new GiftSimpleDto(gift);
            giftResponseDtoList.add(dto);
        }
        return giftResponseDtoList;
    }
    public List<GiftSimpleDto> findGivenGiftList(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftSimpleDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(gift.getIsGiven()){
                GiftSimpleDto dto = new GiftSimpleDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }
    public List<GiftSimpleDto> findReceivedGiftList(Long friendNo){
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        List<GiftSimpleDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(!gift.getIsGiven()){
                GiftSimpleDto dto = new GiftSimpleDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }


    //RETURN
    public FriendResponseDto returnDto(Friend friend){
        User friendUser = findUserByUserNo(friend.getFriendUserNo());

        List<Reminder> reminderList = friendUser.getReminderList();
        List<ReminderSimpleDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : reminderList){
            Friend reminderFriend = r.getFriend();
            FriendSimpleDto friendsimpleDto = null;
            if(reminderFriend != null) {
                User reminderFriendUser = findUserByUserNo(reminderFriend.getFriendUserNo());
                UserPhoto photo = reminderFriendUser.getUserProfilePhoto();
                friendsimpleDto = FriendSimpleDto.from(reminderFriend, photo);
            }
            reminderDtoList.add(new ReminderSimpleDto(r, friendsimpleDto));
        }
        List<Favor> favorList = new ArrayList<>();
        for(Integer favorType : friendUser.getFavorList()){
            favorList.add(Favor.valueOf(favorType));
        }
        List<AnniversaryResponseDto> anniversaryList = new ArrayList<>();
        for(Anniversary a : friendUser.getAnniversaryList()){
            anniversaryList.add(AnniversaryResponseDto.from(a));
        }
        HashMap<String, Integer> giftInfo = returnGiftInfo(friend.getFriendNo());

        String friendId = friendUser.getUserId();

        return new FriendResponseDto(friend, friendUser, reminderDtoList, favorList, anniversaryList, giftInfo, friendId);
    }

    public HashMap<String, Integer> returnGiftInfo(Long friendNo) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        List<Gift> giftList = giftRepository.findGiftsByFriendNoListContains(friendNo);
        hashMap.put("total", giftList.size());

        int given = 0;
        int received = 0;
        for (Gift gift : giftList) {
            if (gift.getIsGiven()) given++;
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
