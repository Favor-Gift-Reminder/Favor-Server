package com.favor.favor.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoUpdateRequestDto {
    @ApiModelProperty(position = 1, required = false, value = "메모", example = "메모수정완료")
    private String memo;
}
