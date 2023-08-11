package com.favor.favor.common.enums;

import lombok.Getter;

public enum CategoryGift {
//    가벼운선물, 생일, 집들이, 시험, 승진, 졸업, 기타
    가벼운선물(1),
    생일(2),
    집들이(3),
    시험(4),
    승진(5),
    졸업(6),
    기타(7);

    @Getter
    Integer type;
    CategoryGift(Integer type){
        this.type = type;
    }

    public static CategoryGift validateType(String category){
        try{
            return CategoryGift.valueOf(category);
        }catch(IllegalStateException e){
            //커스텀 전 임시
            throw new IllegalArgumentException();
        }
    }

    public static CategoryGift valueOf(Integer type){
        CategoryGift categoryGift = null;
        switch (type){
            case 1:
                categoryGift = CategoryGift.가벼운선물;
                break;
            case 2:
                categoryGift = CategoryGift.생일;
                break;
            case 3:
                categoryGift = CategoryGift.집들이;
                break;
            case 4:
                categoryGift = CategoryGift.시험;
                break;
            case 5:
                categoryGift = CategoryGift.승진;
                break;
            case 6:
                categoryGift = CategoryGift.졸업;
                break;
            case 7:
                categoryGift = CategoryGift.기타;
                break;
            default:
                break;
        }
        return categoryGift;
    }
}
