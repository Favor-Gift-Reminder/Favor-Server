package com.favor.favor.User;

import com.favor.favor.Common.ValidationSequence;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileDto {
    @ApiModelProperty(position = 1, required = true, value = "아이디", example = "Favor")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$", message = "비밀번호는 영문과 숫자를 포함하여 8자 이상이어야 합니다.")
    private String userId;

    @ApiModelProperty(position = 2, required = true, value = "이름", example = "페이버")
    private String name;
}
