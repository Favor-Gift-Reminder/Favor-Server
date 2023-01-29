package com.favor.favor.Friend;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendListResponseDto {
    private Boolean isUser;
    private Long friendNo;
    private String friendName;

    @Builder
    public FriendListResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
    }
}
