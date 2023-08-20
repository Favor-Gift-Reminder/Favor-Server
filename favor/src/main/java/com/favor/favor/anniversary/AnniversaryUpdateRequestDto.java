package com.favor.favor.anniversary;

import com.favor.favor.common.enums.AnniversaryCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnniversaryUpdateRequestDto {

    @ApiModelProperty(position = 1, required = true, value = "제목", example = "제목")
    private String anniversaryTitle;

    @ApiModelProperty(position = 2, required = true, value = "날짜", example = "1996-02-29")
    private String anniversaryDate;

    @ApiModelProperty(position = 3, required = true, value = "종류", example = "축하_생일")
    private AnniversaryCategory anniversaryCategory;

    @ApiModelProperty(position = 4, required = true, value = "핀 여부", example = "false")
    private Boolean isPinned;
}
