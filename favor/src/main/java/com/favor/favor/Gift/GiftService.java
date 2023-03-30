package com.favor.favor.Gift;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public GiftDetailResponseDto createGift(GiftRequestDto giftRequestDto, Long userNo, Long friendNo){
        User user =  userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, friendNo));
        addGiftNo(gift.getGiftNo(), friendNo);
        return new GiftDetailResponseDto(gift);
    }
    public void addGiftNo(Long giftNo, Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Long> giftNoList = friend.getGiftNoList();
        giftNoList.add(giftNo);
        friend.setGiftNoList(giftNoList);
    }

    public GiftDetailResponseDto readGift(Long giftNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftDetailResponseDto dto = new GiftDetailResponseDto(gift);
        return dto;
    }

    public GiftDetailResponseDto updateGift(GiftUpdateRequestDto dto, Long giftNo, Long friendNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        gift.setGiftName(dto.getGiftName());
        gift.setGiftDate(dto.getGiftDate());
        gift.setGiftMemo(dto.getGiftMemo());
        gift.setCategory(dto.getCategory());
        gift.setEmotion(dto.getEmotion());
        gift.setIsPinned(dto.getIsPinned());
        gift.setIsGiven(dto.getIsGiven());
        gift.setFriendNo(friendNo);

        giftRepository.save(gift);
        return new GiftDetailResponseDto(gift);
    }

    public GiftDetailResponseDto deleteGift(Long giftNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftDetailResponseDto returnDto = new GiftDetailResponseDto(gift);
        giftRepository.deleteById(giftNo);
        return returnDto;
    }


    public List<GiftDetailResponseDto> readAll(){
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftRepository.findAll()){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }
        return g_List;
    }
}
