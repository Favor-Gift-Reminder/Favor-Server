package com.favor.favor.Gift;

import com.favor.favor.Exception.CustomException;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.Friend.FriendResponseDto;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.Exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public Gift createGift(GiftRequestDto giftRequestDto, Long userNo, Long friendNo){
        User user =  findUserByUserNo(userNo);
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, friendNo));
        addGiftNo(gift.getGiftNo(), friendNo);
        return gift;
    }
    public void addGiftNo(Long giftNo, Long friendNo){
        Friend friend = findFriendByFriendNo(friendNo);
        List<Long> giftNoList = friend.getGiftNoList();
        giftNoList.add(giftNo);
        friend.setGiftNoList(giftNoList);
    }

    public void updateGift(GiftUpdateRequestDto dto, Gift gift, Long friendNo){
        gift.setGiftName(dto.getGiftName());
        gift.setGiftDate(dto.getGiftDate());
        gift.setGiftMemo(dto.getGiftMemo());
        gift.setCategory(dto.getCategory());
        gift.setEmotion(dto.getEmotion());
        gift.setIsPinned(dto.getIsPinned());
        gift.setIsGiven(dto.getIsGiven());
        gift.setFriendNo(friendNo);
    }

    public void deleteGift(Long giftNo){
        giftRepository.deleteById(giftNo);
    }


    public List<GiftResponseDto> readAll(){
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftRepository.findAll()){
            GiftResponseDto dto = new GiftResponseDto(g);
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
        Gift gift = null;
        try{
            gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch(RuntimeException e){
            throw new CustomException(e, GIFT_NOT_FOUND);
        }
        return gift;
    }


    public GiftResponseDto returnDto(Gift gift){
        return new GiftResponseDto(gift);
    }

    public FriendResponseDto returnFriendDto(Gift gift){
        Friend friend = null;
        try{
            friend = friendRepository.findByFriendNo(gift.getFriendNo()).orElseThrow(
                    () -> new RuntimeException()
            );
        }catch (RuntimeException e){
            throw new CustomException(e, FRIEND_NOT_FOUND);
        }
        return new FriendResponseDto(friend);
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
