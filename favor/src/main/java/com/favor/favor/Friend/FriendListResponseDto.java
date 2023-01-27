package com.favor.favor.Friend;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendListResponseDto {
    private Boolean isUser;
    private String friendName;

    @Builder
    public FriendListResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendName = friend.getFriendName();
    }
}
