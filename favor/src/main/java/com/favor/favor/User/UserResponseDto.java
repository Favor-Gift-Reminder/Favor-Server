package com.favor.favor.User;

import com.favor.favor.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private final Long userNo;
    private String name;
    private String userid;
    private Role role;

    @Builder
    public UserResponseDto(User user){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
    }
}
