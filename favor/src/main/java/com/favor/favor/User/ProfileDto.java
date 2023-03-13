package com.favor.favor.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileDto {
    @ApiModelProperty(position = 1, required = true, value = "아이디", example = "Favor")
    private String userId;

    @ApiModelProperty(position = 2, required = true, value = "이름", example = "페이버")
    private String name;
}
