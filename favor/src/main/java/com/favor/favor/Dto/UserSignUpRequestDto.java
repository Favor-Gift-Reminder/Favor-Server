package com.favor.favor.Dto;

import com.favor.favor.Common.FavorType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "이메일", example = "favor@gmail.com")
    @Email(message = "이메일 형식이 잘못되었습니다")
    private String email;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "비밀번호", example = "********")
    @Length(min = 8, message = "비밀번호는 8자 이상이어야합니다")
    private String password;

    @ApiModelProperty(position = 3, required = true, dataType = "String", value = "아이디", example = "My ID")
    private String userId;

    @ApiModelProperty(position = 4, required = true, dataType = "String", value = "이름", example = "My Name")
    private String name;


}
