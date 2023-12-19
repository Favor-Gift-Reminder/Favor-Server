package com.favor.favor.gift;

import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public Gift createGift(GiftRequestDto giftRequestDto, Long userNo){
        log.info("[SERVICE] [createGift] 실행");
        User user =  findUserByUserNo(userNo);
        log.info("[SERVICE] user = {}", user);
        LocalDate localDate = returnLocalDate(giftRequestDto.getGiftDate());
        log.info("[SERVICE] locaDate = {}", localDate);
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, localDate));
        log.info("[SERVICE] gift = {}", gift);
        addGiftNo(gift.getGiftNo(), gift.getFriendNoList());

        return giftRepository.save(gift);
    }
    @Transactional
    public void addGiftNo(Long giftNo, List<Long> friendNoList){
        for(Long friendNo : friendNoList) {
            Friend friend = findFriendByFriendNo(friendNo);
            List<Long> giftNoList = friend.getGiftNoList();

            if(!giftNoList.contains(giftNo)) giftNoList.add(giftNo);

            friend.updateGiftNoList(giftNoList);
        }
    }

    @Transactional
    public void updateGift(GiftUpdateRequestDto dto, Gift gift){
        //수정된 gift 정보 저장 (친구 목록 제외)
        gift.setGiftName(dto.getGiftName());
        gift.setGiftMemo(dto.getGiftMemo());
        gift.setCategory(dto.getGiftCategory());
        gift.setEmotion(dto.getEmotion());
        gift.setIsGiven(dto.getIsGiven());
        gift.setGiftDate(returnLocalDate(dto.getGiftDate()));

        List<Long> existingFriendNoList = new ArrayList<>(gift.getFriendNoList());
        List<Long> updatedFriendNoList = dto.getFriendNoList();

        //친구 목록 수정 시
        //빠진 친구들의 선물 목록에서 선물 식별자 삭제
        //@Transactional 없는 경우 ConcurrentModificationException 발생
        for (Long friendNo : existingFriendNoList) {
            if (!updatedFriendNoList.contains(friendNo)) {
                Friend friend = findFriendByFriendNo(friendNo);
                friend.getGiftNoList().remove(gift.getGiftNo());
                friendRepository.save(friend);
                //선물의 친구 식별자 목록에서 빠진 친구 삭제
                gift.removeFriendNo(friendNo);
            }
        }
        //추가된 친구들의 선물 목록에 선물 식별자 추가
        for (Long friendNo : updatedFriendNoList) {
            if (!existingFriendNoList.contains(friendNo)) {
                Friend friend = findFriendByFriendNo(friendNo);
                friend.getGiftNoList().add(gift.getGiftNo());
                friendRepository.save(friend);
                //선물의 친구 식별자 목록에 추가된 친구 추가
                gift.addFriendNo(friendNo);
            }
        }
        giftRepository.save(gift);
    }

    @Transactional
    public void updateIsPinned(Gift gift){
        gift.setIsPinned(gift.getIsPinned() == true ? false : true);
        giftRepository.save(gift);
    }

    @Transactional
    public void updateTempFriendList(Gift gift, GiftTempFriendListDto tempFriendList){
        gift.setTempFriendList(tempFriendList);
        giftRepository.save(gift);
    }

    @Transactional
    public void deleteGift(Long giftNo){
        Gift gift = findGiftByGiftNo(giftNo);
        log.info("[SERVICE] gift = {}", gift);
        List<Long> friendNoList = gift.getFriendNoList();
        log.info("[SERVICE] friendNoList = {}", friendNoList);

        //선물 삭제시 선물을 보유하고 있던 유저의 선물 식별자 목록에서 선물 식별자가 사라짐
        for(Long friendNo : friendNoList){
            Friend friend = findFriendByFriendNo(friendNo);
            log.info("[SERVICE] friend = {}", friend);
            friend.getGiftNoList().remove(giftNo);
        }

        giftRepository.deleteById(giftNo);
    }

    public List<GiftResponseDto> readAll(){
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift gift : giftRepository.findAll()){
            GiftResponseDto dto = returnDto(gift);
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


    // Gift만 입력받아도 FriendList 를 가진 responseDTO 를 반환
    @Transactional
    public GiftResponseDto returnDto(Gift gift){

        List<Long> friendNoList = gift.getFriendNoList();

        List<FriendSimpleDto> friendResponseDtoList = new ArrayList<>();
        for(Long friendNo : friendNoList){
            Friend friend = findFriendByFriendNo(friendNo);
            User friendUser = findUserByUserNo(friend.getFriendUserNo());
            UserPhoto photo = friendUser.getUserProfilePhoto();
            friendResponseDtoList.add(FriendSimpleDto.from(friend, photo));
        }

        giftRepository.save(gift);
        log.info("[SYSTEM] giftRepository.save(gift) 완료");

        return new GiftResponseDto(gift, friendResponseDtoList);
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
