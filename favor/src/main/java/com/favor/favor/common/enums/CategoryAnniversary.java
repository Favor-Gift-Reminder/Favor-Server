package com.favor.favor.common.enums;

import lombok.Getter;

public enum CategoryAnniversary {
    연인(1),
    축하_생일(2),
    졸업(3),
    합격(4),
    입사_승진(5),
    이사_집들이(6),
    출산(7);

    @Getter
    Integer type;

    CategoryAnniversary(Integer type){
        this.type = type;
    }

    public static  CategoryAnniversary validateType(String category){
        try{
            return CategoryAnniversary.valueOf(category);
        }catch(IllegalStateException e){
            throw new IllegalArgumentException();
        }

    }

    public static CategoryAnniversary valueOf(Integer type){
        CategoryAnniversary categoryAnniversary = null;
        switch (type){
            case 1:
                categoryAnniversary = CategoryAnniversary.연인;
                break;
            case 2:
                categoryAnniversary = CategoryAnniversary.축하_생일;
                break;
            case 3:
                categoryAnniversary = CategoryAnniversary.졸업;
                break;
            case 4:
                categoryAnniversary = CategoryAnniversary.합격;
                break;
            case 5:
                categoryAnniversary = CategoryAnniversary.입사_승진;
                break;
            case 6:
                categoryAnniversary = CategoryAnniversary.이사_집들이;
                break;
            case 7:
                categoryAnniversary = CategoryAnniversary.출산;
                break;
        }
        return categoryAnniversary;
    }
}
