package com.favor.favor.anniversary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnniversaryResponseDto {
    private Long anniversaryNo;
    private String anniversaryTitle;
    private LocalDate anniversaryDate;
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
