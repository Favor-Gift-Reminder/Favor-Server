package com.favor.favor.User;

import com.favor.favor.Common.Favor;
import com.favor.favor.Common.Role;
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
public class UserRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "이메일", example = "favor@gmail.com")
    @Email(message = "이메일 형식이 잘못되었습니다")
    private String email;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "비밀번호", example = "********")
    @Length(min = 8, message = "8자 이상 입력해주세요")
    private String password;

    @ApiModelProperty(position = 3, required = true, dataType = "String", value = "이름", example = "페이버")
    private String name;

    @ApiModelProperty(position = 4, required = true, dataType = "String", value = "아이디", example = "Favor")
    private String userId;

    @ApiModelProperty(position = 5, required = true, dataType = "Role", value = "권한", example = "USER")
    private Role role;

}
