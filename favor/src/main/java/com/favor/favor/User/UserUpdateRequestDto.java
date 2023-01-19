package com.favor.favor.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    //이름, ID, 취향, 기념일 수정 가능
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "이름", example = "페이버")
    private String name;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "아이디", example = "Favor")
    private String userId;
}
