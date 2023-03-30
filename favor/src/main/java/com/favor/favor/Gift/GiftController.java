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
    @PostMapping("/{userNo}/{friendNo}")
    public GiftResponseDto createGift(@RequestBody GiftRequestDto dto, @PathVariable("userNo") Long userNo, @PathVariable("friendNo") Long friendNo){
        return giftService.createGift(dto, userNo, friendNo);
    }

    @ApiOperation("단일 선물 조회")
    @GetMapping("/{giftNo}")
    public GiftResponseDto readGift(@PathVariable Long giftNo){
        return giftService.readGift(giftNo);
    }

    @ApiOperation("선물 수정")
    @PatchMapping("/{giftNo}")
    public GiftResponseDto updateGift(@RequestBody GiftUpdateRequestDto dto, @PathVariable Long giftNo, Long friendNo){
        return giftService.updateGift(dto, giftNo, friendNo);
    }

    @ApiOperation("선물 삭제")
    @DeleteMapping("/{giftNo}")
    public GiftResponseDto deleteGift(@PathVariable Long giftNo){
        return giftService.deleteGift(giftNo);
    }

    @ApiOperation("전체 선물 조회")
    @GetMapping
    public List<GiftResponseDto> readAll(){
        return giftService.readAll();
    }
}
