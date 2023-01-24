package com.favor.favor.Gift;

import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;

    public void createGift(GiftRequestDto giftRequestDto, Long userNo){
        User user =  userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        giftRepository.save(giftRequestDto.toEntity(user));
    }

    public GiftResponseDto readGift(Long giftNo){
        Gift gift = giftRepository.findById(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftResponseDto dto = new GiftResponseDto(gift);
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

        giftRepository.save(gift);
        return giftNo;
    }

    public Long 
}
