package com.favor.favor.gift;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GiftTempFriendListDto {
    @ApiModelProperty(position = 1, required = false, value = "임시 친구 목록", example = "[\"차은우\",\n\"김채원\"]")
    private List<String> tempFrindList;
}
