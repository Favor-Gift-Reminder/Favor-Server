package com.favor.favor.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class signUpDto {
    @ApiModelProperty(position = 1, required = true, value = "이메일", example = "favor@gmail.com")
    @Email(message = "이메일 형식이 잘못되었습니다")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private String email;

    @ApiModelProperty(position = 2, required = true, value = "비밀번호", example = "1q2w3e4r!")
    @Length(min = 8, message = "8자 이상 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*\\\\W).{8,20}$", message = "비밀번호는 영문과 특수문자를 포함하여 8자 이상이어야 합니다.")
    private String password;
}
