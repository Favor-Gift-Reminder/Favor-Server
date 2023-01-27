package com.favor.favor.Gift;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public Long createGift(GiftRequestDto giftRequestDto, Long userNo, Long friendNo){
        User user =  userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend friend = friendRepository.findById(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, friend));
        return gift.getGiftNo();
    }

    public GiftDetailResponseDto readGift(Long giftNo){
        Gift gift = giftRepository.findById(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftDetailResponseDto dto = new GiftDetailResponseDto(gift);
        return dto;
    }

    public Long updateGift(Long giftNo, GiftUpdateRequestDto dto){
        Gift gift = giftRepository.findById(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        gift.setGiftName(dto.getGiftName());
        gift.setGiftDate(dto.getGiftDate());
        gift.setGiftMemo(dto.getGiftMemo());
        gift.setCategory(dto.getCategory());
        gift.setEmotion(dto.getEmotion());
        gift.setIsPinned(dto.getIsPinned());
        gift.setIsGiven(dto.getIsGiven());
        gift.setFriend(dto.getFriend());

        giftRepository.save(gift);
        return giftNo;
    }

    public Long deleteGift(Long giftNo){
        giftRepository.deleteById(giftNo);
        return giftNo;
    }
}
