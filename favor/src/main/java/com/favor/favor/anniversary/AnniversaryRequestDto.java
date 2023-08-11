package com.favor.favor.anniversary;

import com.favor.favor.common.enums.CategoryAnniversary;
import com.favor.favor.common.enums.CategoryGift;
import com.favor.favor.user.User;
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

    @ApiModelProperty(position = 3, required = true, value = "종류", example = "축하_생일")
    private CategoryAnniversary categoryAnniversary;

    @Transactional
    public Anniversary toEntity(User user, LocalDate localDate){
        return Anniversary.builder()
                .anniversaryTitle(anniversaryTitle)
                .anniversaryDate(localDate)
                .category(categoryAnniversary.getType())
                .isPinned(false)
                .user(user)
                .build();
    }
}
