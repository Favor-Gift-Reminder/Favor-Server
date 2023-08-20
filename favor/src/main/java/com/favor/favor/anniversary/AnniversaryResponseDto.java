package com.favor.favor.anniversary;

import com.favor.favor.common.enums.AnniversaryCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnniversaryResponseDto {
    private Long anniversaryNo;
    private String anniversaryTitle;
    private LocalDate anniversaryDate;
    private Boolean isPinned;
    private Long userNo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private AnniversaryCategory anniversaryCategory;

    @Builder
    public AnniversaryResponseDto(Anniversary anniversary){
        this.anniversaryNo = anniversary.getAnniversaryNo();
        this.anniversaryTitle = anniversary.getAnniversaryTitle();
        this.anniversaryDate = anniversary.getAnniversaryDate();
        this.isPinned = anniversary.getIsPinned();
        this.userNo = anniversary.getUser().getUserNo();
        this.createdAt = anniversary.getCreatedAt();
        this.modifiedAt = anniversary.getModifiedAt();
        this.anniversaryCategory = AnniversaryCategory.valueOf(anniversary.getCategory());
    }
}
