package com.favor.favor.photo;

import com.favor.favor.common.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    @Builder
    public Photo(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFileName(String fileName){

        int extensionIndex = fileName.lastIndexOf('.');
        String originalFileName = fileName.substring(extensionIndex);

        return originalFileName;
    }
}
