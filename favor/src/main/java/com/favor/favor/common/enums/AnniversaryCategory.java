package com.favor.favor.common.enums;

import lombok.Getter;

public enum AnniversaryCategory {
    연인(1),
    축하_생일(2),
    졸업(3),
    합격(4),
    입사_승진(5),
    이사_집들이(6),
    출산(7);

    @Getter
    Integer type;

    AnniversaryCategory(Integer type){
        this.type = type;
    }

    public static AnniversaryCategory validateType(String category){
        try{
            return AnniversaryCategory.valueOf(category);
        }catch(IllegalStateException e){
            throw new IllegalArgumentException();
        }

    }

    public static AnniversaryCategory valueOf(Integer type){
        AnniversaryCategory anniversaryCategory = null;
        switch (type){
            case 1:
                anniversaryCategory = AnniversaryCategory.연인;
                break;
            case 2:
                anniversaryCategory = AnniversaryCategory.축하_생일;
                break;
            case 3:
                anniversaryCategory = AnniversaryCategory.졸업;
                break;
            case 4:
                anniversaryCategory = AnniversaryCategory.합격;
                break;
            case 5:
                anniversaryCategory = AnniversaryCategory.입사_승진;
                break;
            case 6:
                anniversaryCategory = AnniversaryCategory.이사_집들이;
                break;
            case 7:
                anniversaryCategory = AnniversaryCategory.출산;
                break;
        }
        return anniversaryCategory;
    }
}
