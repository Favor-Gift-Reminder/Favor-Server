package com.favor.favor.anniversary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class AnniversaryResponseDto {
    private final Long anniversaryNo;
    private String anniversaryTitle;
    private Date anniversaryDate;
    private Boolean isPinned;
    private Long userNo;

    @Builder
    public AnniversaryResponseDto(Anniversary anniversary){
        this.anniversaryNo = anniversary.getAnniversaryNo();
        this.anniversaryTitle = anniversary.getAnniversaryTitle();
        this.anniversaryDate = anniversary.getAnniversaryDate();
        this.isPinned = anniversary.getIsPinned();
        this.userNo = anniversary.getUser().getUserNo();
    }
}
