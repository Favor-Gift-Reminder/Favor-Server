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
    @Pattern(regexp = "^[a-zA-Z0-9._]{3,20}$", message = "아이디는 3글자 이상으로 영문이나 숫자, ._ 만 사용 가능합니다.")
    private String userId;

    @ApiModelProperty(position = 2, required = true, value = "이름", example = "페이버")
    private String name;
}
