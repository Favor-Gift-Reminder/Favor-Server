package com.favor.favor.friend.noAccount;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.common.enums.Favor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendUpdateRequestDto {
    @ApiModelProperty(position = 1, required = false, dataType = "String", value = "친구이름", example = "이름")
    private String friendName;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "친구메모", example = "메모")
    private String friendMemo;

    @ApiModelProperty(position = 3, required = false, value = "취향목록", example = "[\n\"심플한\"\n]")
    private List<Favor> favorList;

    @ApiModelProperty(position = 4, required = false, value = "기념일목록", example = "[\n" +
                                                                                    "    1, 2\n" +
                                                                                    "  ]")
    private List<Long> anniversaryNoList;
}
