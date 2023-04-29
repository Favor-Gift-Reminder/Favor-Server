package com.favor.favor.anniversary;

import com.favor.favor.member.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnniversaryRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "제목", example = "제목")
    private String anniversaryTitle;

    @ApiModelProperty(position = 2, required = true, value = "날짜", example = "1996-02-29")
    private String anniversaryDate;

    @Transactional
    public Anniversary toEntity(User user, LocalDate localDate){
        return Anniversary.builder()
                .anniversaryTitle(anniversaryTitle)
                .anniversaryDate(localDate)
                .isPinned(false)
                .user(user)
                .build();
    }
}
