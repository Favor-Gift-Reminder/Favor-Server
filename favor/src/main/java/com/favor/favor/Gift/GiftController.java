package com.favor.favor.Gift;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Gift")
@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
@Log4j2
public class GiftController {
    private final GiftService giftService;

    @ApiOperation("선물 생성")
    @PostMapping
    public Long createGift(@RequestBody GiftRequestDto dto, Long userNo, Long friendNo){
        return giftService.createGift(dto, userNo, friendNo);
    }

    @ApiOperation("단일 선물 조회")
    @GetMapping("/{giftNo}")
    public GiftDetailResponseDto readGift(@PathVariable Long giftNo){
        return giftService.readGift(giftNo);
    }

    @ApiOperation("선물 수정")
    @PatchMapping("/{giftNo}")
    public Long updateGift(@RequestBody GiftUpdateRequestDto dto, @PathVariable Long giftNo, Long userNo, Long friendNo){
        return giftService.updateGift(giftNo, dto, friendNo);
    }

    @ApiOperation("선물 삭제")
    @DeleteMapping("/{giftNo}")
    public Long deleteGift(@PathVariable Long giftNo){
        return giftService.deleteGift(giftNo);
    }

    @ApiOperation("전체 선물 조회")
    @GetMapping
    public List<GiftResponseDto> readAll(){
        return giftService.readAll();
    }

    @ApiOperation("이름으로 회원 선물 조회")
    @GetMapping("/by-name/{userNo}/{giftName}")
    public List<GiftDetailResponseDto> readGiftListByname (@PathVariable("userNo") Long userNo, @PathVariable("giftName") String giftName){
        return giftService.readGiftListByName(userNo, giftName);
    }

    @ApiOperation("카테고리로 회원 선물 조회")
    @GetMapping("/by-dategory/{userNo}/{category}")
    public List<GiftDetailResponseDto> readGiftListByCategory(@PathVariable("userNo") Long userNo, @PathVariable("category") String category){
        return giftService.readGiftListByCategory(userNo, category);
    }

    @ApiOperation("감정으로 회원 선물 검색")
    @GetMapping("/by-emotion/{userNo}/{emotion}")
    public List<GiftDetailResponseDto> readGiftListByEmotion(@PathVariable("userNo") Long userNo, @PathVariable("emotion") String emotion){
        return giftService.readGiftListByEmotion(userNo, emotion);
    }
}
