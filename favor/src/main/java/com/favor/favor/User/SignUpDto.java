package com.favor.favor.User;

import com.favor.favor.Common.ValidationSequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@GroupSequence({
        SignUpDto.class,
        ValidationSequence.NotBlank.class,
        ValidationSequence.Email.class,
        ValidationSequence.Size.class,
        ValidationSequence.Pattern.class
})

@ApiModel(value = "회원가입")
public class SignUpDto {
    @ApiModelProperty(position = 1, required = true, value = "이메일", example = "favor@gmail.com")
    @NotBlank(message = "이메일을 입력해주세요", groups = ValidationSequence.NotBlank.class)
    @Email(message = "이메일 형식이 잘못되었습니다", groups = ValidationSequence.Email.class)
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", groups = ValidationSequence.Pattern.class)
    private String email;

    @ApiModelProperty(position = 2, required = true, value = "비밀번호", example = "1q2w3e4r!")
    @NotBlank(message = "비밀번호를 입력해주세요", groups = ValidationSequence.NotBlank.class)
    @Length(min = 8, max = 20, message = "8~20자로 입력해주세요", groups = ValidationSequence.Size.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*\\\\W).{8,20}$", message = "비밀번호는 영문과 특수문자를 포함하여 8자 이상이어야 합니다.", groups = ValidationSequence.Pattern.class)
    private String password;
}
