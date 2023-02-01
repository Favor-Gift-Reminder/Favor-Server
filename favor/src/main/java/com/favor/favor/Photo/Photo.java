package com.favor.favor.Photo;

import com.favor.favor.Common.TimeStamped;
import com.favor.favor.User.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Transactional
public class Photo extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoNo;

    @NotBlank
    private String originalFileName;

    @NotBlank
    private String filePath;

    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "photo_user",
            joinColumns = @JoinColumn(name = "photo_photo_no"),
            inverseJoinColumns = @JoinColumn(name = "user_user_no"))
    private User user;

    @Builder
    public Photo(String originalFileName, String filePath){
        this.originalFileName = originalFileName;
        this.filePath = filePath;
    }

    public void setUser(User user){
        this.user = user;
    }
}
