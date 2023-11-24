package com.favor.favor.anniversary;

import com.favor.favor.common.TimeStamped;
import com.favor.favor.common.enums.AnniversaryCategory;
import com.favor.favor.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Anniversary extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anniversaryNo;

    private String anniversaryTitle;

    private LocalDate anniversaryDate;

    private Integer category;

    private Boolean isPinned;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_no")
    private User user;

    public void updateAnniversaryTitle(String anniversaryTitle){ this.anniversaryTitle = anniversaryTitle; }

    public void updateAnniversaryDate(LocalDate anniversaryDate){ this.anniversaryDate = anniversaryDate; }

    public void updateCategory(AnniversaryCategory anniversaryCategory){
        this.category = anniversaryCategory.getType();
    }

    public void updateIsPinned(Boolean isPinned){ this.isPinned = isPinned; }

    @Builder
    public Anniversary(String anniversaryTitle, LocalDate anniversaryDate, Integer category, Boolean isPinned, User user) {
        this.anniversaryTitle = anniversaryTitle;
        this.anniversaryDate = anniversaryDate;
        this.category = category;
        this.isPinned = isPinned;
        this.user = user;
    }
}
