package com.favor.favor.Gift;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendRepository;
import com.favor.favor.User.User;
import com.favor.favor.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Friend friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new RuntimeException()
        );
        Gift gift = giftRepository.save(giftRequestDto.toEntity(user, friend));
        return gift.getGiftNo();
    }

    public GiftDetailResponseDto readGift(Long giftNo){
        Gift gift = giftRepository.findByGiftNo(giftNo).orElseThrow(
                () -> new RuntimeException()
        );
        GiftDetailResponseDto dto = new GiftDetailResponseDto(gift);
        return dto;
    }

    public Long updateGift(Long giftNo, GiftUpdateRequestDto dto, Long friendNo){
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
        gift.setFriend(friend);

        giftRepository.save(gift);
        return giftNo;
    }

    public Long deleteGift(Long giftNo){
        giftRepository.deleteById(giftNo);
        return giftNo;
    }


    public List<GiftResponseDto> readAll(){
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftRepository.findAll()){
            GiftResponseDto dto = new GiftResponseDto(g);
            g_List.add(dto);
        }
        return g_List;
    }

    public List<GiftDetailResponseDto> readGiftListByName(Long userNo, String giftName){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndGiftName(user, giftName);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftDetailResponseDto> readGiftListByCategory(Long userNo, String category){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndCategory(user, category);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftDetailResponseDto> readGiftListByEmotion(Long userNo, String emotion){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndEmotion(user, emotion);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }
}
