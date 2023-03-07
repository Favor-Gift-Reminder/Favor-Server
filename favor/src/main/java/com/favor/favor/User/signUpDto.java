package com.favor.favor.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class signUpDto {
    @ApiModelProperty(position = 1, required = true, value = "이메일", example = "favor@gmail.com")
    @Email(message = "이메일 형식이 잘못되었습니다")
    private String email;

    @ApiModelProperty(position = 2, required = true, value = "비밀번호", example = "********")
    @Length(min = 8, message = "8자 이상 입력해주세요")
    private String password;
}
