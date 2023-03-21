package com.favor.favor.User;

import com.favor.favor.Common.ValidationSequence;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UserUpdatePasswordDto {

    @ApiModelProperty(position = 1, required = true, value = "비밀번호", example = "1q2w3e4r!")
    @NotBlank(message = "변경할 비밀번호를 입력해주세요", groups = ValidationSequence.NotBlank.class)
    @Length(min = 8, max = 20, message = "비밀번호는 8~20자로 입력해주세요", groups = ValidationSequence.Size.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$", message = "비밀번호는 영문과 숫자를 포함하여 8자 이상이어야 합니다.", groups = ValidationSequence.Pattern.class)
    private String password;
}
