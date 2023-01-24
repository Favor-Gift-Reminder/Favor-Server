package com.favor.favor.Gift;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Gift")
@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
@Log4j2
public class GiftController {
    private final GiftService giftService;

    @ApiOperation("선물 생성")
    @PostMapping
    public void createGift(@RequestBody GiftRequestDto dto, Long userNo){
        giftService.createGift(dto, userNo);
    }

    @ApiOperation("단일선물 조회")
    @GetMapping("/{giftNo}")
    public GiftResponseDto readGift(@PathVariable Long giftNo){

    }

    @ApiOperation("선물 수정")
    @PatchMapping("/{giftNo}")
    public Long updateGift(@PathVariable Long giftNo){

    }


    @ApiOperation("선물 삭제")
    @DeleteMapping("/{giftNo}")
    public Long deleteGift(@PathVariable Long giftNo){

    }

}
