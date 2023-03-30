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

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public GiftResponseDto createGift(GiftRequestDto giftRequestDto, Long userNo, Long friendNo){
        User user =  userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, friendNo));
        addGiftNo(gift.getGiftNo(), friendNo);
        return new GiftResponseDto(gift);
    }
    public void addGiftNo(Long giftNo, Long friendNo){
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Long> giftNoList = friend.getGiftNoList();
        giftNoList.add(giftNo);
        friend.setGiftNoList(giftNoList);
    }

    public GiftResponseDto readGift(Long giftNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftResponseDto dto = new GiftResponseDto(gift);
        return dto;
    }

    public GiftResponseDto updateGift(GiftUpdateRequestDto dto, Long giftNo, Long friendNo){
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
        return new GiftResponseDto(gift);
    }

    public GiftResponseDto deleteGift(Long giftNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftResponseDto returnDto = new GiftResponseDto(gift);
        giftRepository.deleteById(giftNo);
        return returnDto;
    }


    public List<GiftResponseDto> readAll(){
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftRepository.findAll()){
            GiftResponseDto dto = new GiftResponseDto(g);
            g_List.add(dto);
        }
        return g_List;
    }
}
