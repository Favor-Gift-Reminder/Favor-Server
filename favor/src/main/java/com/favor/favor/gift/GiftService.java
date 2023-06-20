package com.favor.favor.gift;

import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public Gift createGift(GiftRequestDto giftRequestDto, Long userNo){
        log.info("[Service] [createGift] 실행");
        User user =  findUserByUserNo(userNo);
        LocalDate localDate = returnLocalDate(giftRequestDto.getGiftDate());
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, localDate));
        addGiftNo(gift.getGiftNo(), gift.getFriendNoList());
        return giftRepository.save(gift);
    }
    @Transactional
    public void addGiftNo(Long giftNo, List<Long> friendNoList){
        for(Long friendNo : friendNoList) {
            Friend friend = findFriendByFriendNo(friendNo);
            List<Long> giftNoList = friend.getGiftNoList();

            boolean flag = true;
            for(Long no : friend.getGiftNoList()){
                if (no == giftNo) {
                    flag = false;
                    break;
                }
            }
            if(flag) giftNoList.add(giftNo);



            friend.setGiftNoList(giftNoList);
        }
    }

    public void updateGift(GiftUpdateRequestDto dto, Gift gift){
        gift.setGiftName(dto.getGiftName());
        gift.setGiftMemo(dto.getGiftMemo());
        gift.setCategory(dto.getCategory());
        gift.setEmotion(dto.getEmotion());
        gift.setIsGiven(dto.getIsGiven());
        gift.setGiftDate(returnLocalDate(dto.getGiftDate()));

        List<Long> existingFriendNoList = gift.getFriendNoList();
        List<Long> updatedFriendNoList = dto.getFriendNoList();

        for (Long friendNo : existingFriendNoList) {
            if (!updatedFriendNoList.contains(friendNo)) {
                Friend friend = findFriendByFriendNo(friendNo);
                friend.getGiftNoList().remove(gift.getGiftNo());
                friendRepository.save(friend);
            }
        }

        gift.setFriendNoList(dto.getFriendNoList());
        addGiftNo(gift.getGiftNo(), dto.getFriendNoList());

        giftRepository.save(gift);
    }
    public void updateIsPinned(Gift gift){
        gift.setIsPinned(gift.getIsPinned() == true ? false : true);
        giftRepository.save(gift);
    }

    public void deleteGift(Long giftNo){
        giftRepository.deleteById(giftNo);
    }


    public List<GiftResponseDto> readAll(){
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift gift : giftRepository.findAll()){
            List<FriendResponseDto> friendList = new ArrayList<>();
            for(Long f : gift.getFriendNoList()){
                Friend friend = findFriendByFriendNo(f);
                FriendResponseDto dto = new FriendResponseDto(friend);
                friendList.add(dto);
            }
            GiftResponseDto dto = new GiftResponseDto(gift, friendList);
            g_List.add(dto);
        }
        return g_List;
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

    public Gift findGiftByGiftNo(Long giftNo){
        log.info("[Service] [findGiftByGiftNo] 실행");
        Gift gift = null;
        try{
            log.info("[Service] [findGiftByGiftNo] [try]");
            gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch(RuntimeException e){
            log.info("[Service] [findGiftByGiftNo] [catch]");
            throw new CustomException(e, GIFT_NOT_FOUND);
        }
        return gift;
    }


    public GiftResponseDto returnDto(Gift gift){
        log.info("[Service] [returnDto] 실행");
        List<FriendResponseDto> friendList = new ArrayList<>();
        for(Long f : gift.getFriendNoList()){
            Friend friend = findFriendByFriendNo(f);
            FriendResponseDto dto = new FriendResponseDto(friend);
            friendList.add(dto);
        }
        return new GiftResponseDto(gift, friendList);
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
        String pattern = "yyyy-MM-dd HH:mm:ss";
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

    public void isExistingGiftNo(Long giftNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = giftRepository.existsByGiftNo(giftNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, GIFT_NOT_FOUND);
        }
    }
}
