package com.favor.favor.anniversary;

import com.favor.favor.common.enums.AnniversaryCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
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
    private AnniversaryResponseDto(Long anniversaryNo, String anniversaryTitle, LocalDate anniversaryDate, Boolean isPinned, Long userNo, LocalDateTime createdAt, LocalDateTime modifiedAt, AnniversaryCategory anniversaryCategory) {
        this.anniversaryNo = anniversaryNo;
        this.anniversaryTitle = anniversaryTitle;
        this.anniversaryDate = anniversaryDate;
        this.isPinned = isPinned;
        this.userNo = userNo;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.anniversaryCategory = anniversaryCategory;
    }

    public static AnniversaryResponseDto from(Anniversary anniversary) {
        return AnniversaryResponseDto.builder()
                .anniversaryNo(anniversary.getAnniversaryNo())
                .anniversaryTitle(anniversary.getAnniversaryTitle())
                .anniversaryDate(anniversary.getAnniversaryDate())
                .isPinned(anniversary.getIsPinned())
                .userNo(anniversary.getUser().getUserNo())
                .createdAt(anniversary.getCreatedAt())
                .modifiedAt(anniversary.getModifiedAt())
                .anniversaryCategory(AnniversaryCategory.valueOf(anniversary.getCategory()))
                .build();
    }
}
