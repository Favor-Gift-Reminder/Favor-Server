package com.favor.favor.User;

import com.favor.favor.Common.ValidationSequence;
import com.favor.favor.Enum.Favor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@GroupSequence({
        UserUpdateRequestDto.class,
        ValidationSequence.Size.class,
        ValidationSequence.Pattern.class
})
public class UserUpdateRequestDto {
    //이름, ID, 취향, 기념일 수정 가능
    @ApiModelProperty(position = 1, required = false, value = "이름", example = "페이버")
    private String name;

    @ApiModelProperty(position = 2, required = false, value = "아이디", example = "Favor")
    @Length(min = 3, max = 20, message = "아이디는 3~20자로 입력해주세요", groups = ValidationSequence.Size.class)
    @Pattern(regexp = "^[a-z0-9._]{3,20}$", message = "아이디는 3글자 이상이고 소문자 영문과 숫자와 . _ 만 사용 가능합니다.", groups = ValidationSequence.Pattern.class)
    private String userId;

    @ApiModelProperty(position = 3, required = false, value = "취향목록", example = "[\"심플한\", \"귀여운\"]")
    private List<Favor> favorList;
}
