package com.favor.favor.anniversary;

import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnniversaryRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "제목", example = "제목")
    private String anniversaryTitle;

    @ApiModelProperty(position = 2, required = true, value = "날짜", example = "")
    private Date anniversaryDate;

    @Transactional
    public Anniversary toEntity(User user){
        return Anniversary.builder()
                .anniversaryTitle(anniversaryTitle)
                .anniversaryDate(anniversaryDate)
                .user(user)
                .build();
    }
}
