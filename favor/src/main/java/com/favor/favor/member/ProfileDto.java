package com.favor.favor.member;

import com.favor.favor.common.ValidationSequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({
        ProfileDto.class,
        ValidationSequence.NotBlank.class,
        ValidationSequence.Size.class,
        ValidationSequence.Pattern.class
})

@ApiModel(value = "프로필 등록")
public class ProfileDto {
    @ApiModelProperty(position = 1, required = true, value = "아이디", example = "favor")
    @NotBlank(message = "아이디를 입력해주세요", groups = ValidationSequence.NotBlank.class)
    @Length(min = 3, max = 20, message = "아이디는 3~20자로 입력해주세요", groups = ValidationSequence.Size.class)
    @Pattern(regexp = "^[a-z0-9._]{3,20}$", message = "아이디는 3글자 이상이고 소문자 영문과 숫자와 . _ 만 사용 가능합니다.", groups = ValidationSequence.Pattern.class)
    private String userId;

    @ApiModelProperty(position = 2, required = true, value = "이름", example = "페이버")
    @NotBlank(message = "이름을 입력해주세요", groups = ValidationSequence.NotBlank.class)
    private String name;
}
