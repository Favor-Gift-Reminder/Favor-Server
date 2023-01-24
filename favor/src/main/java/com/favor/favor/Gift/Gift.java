package com.favor.favor.Gift;

import com.favor.favor.Common.TimeStamped;
import com.favor.favor.User.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class Gift extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftNo;

    @NotBlank
    private String giftName;

    private LocalDate giftDate;

    private String giftMemo;

    private String category;

    private String emotion;


    @NotNull
    private Boolean isPinned;

    @NotNull
    private Boolean isGiven;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;
}
