package com.favor.favor.anniversary;

import com.favor.favor.common.TimeStamped;
import com.favor.favor.common.enums.Category;
import com.favor.favor.user.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class Anniversary extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anniversaryNo;

    private String anniversaryTitle;
    public void setAnniversaryTitle(String anniversaryTitle){ this.anniversaryTitle = anniversaryTitle; }

    private LocalDate anniversaryDate;
    public void setAnniversaryDate(LocalDate anniversaryDate){ this.anniversaryDate = anniversaryDate; }

    private Integer category;
    public void setCategory(Category category){
        this.category = category.getType();
    }

    private Boolean isPinned;
    public void setIsPinned(Boolean isPinned){ this.isPinned = isPinned; }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_no")
    private User user;

}
