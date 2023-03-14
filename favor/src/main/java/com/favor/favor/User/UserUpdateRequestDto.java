package com.favor.favor.User;

import com.favor.favor.Enum.Favor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class UserUpdateRequestDto {
    //이름, ID, 취향, 기념일 수정 가능
    @ApiModelProperty(position = 1, required = false, dataType = "String", value = "이름", example = "페이버")
    private String name;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "아이디", example = "Favor")
    private String userId;

    @ApiModelProperty(position = 3, required = false, dataType = "List<Favor>", value = "취향목록", example = "[\"심플한\", \"귀여운\"]")
    private List<Favor> favorList;
}
