package com.favor.favor.common.enums;

import lombok.Getter;

public enum GiftCategory {
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
    GiftCategory(Integer type){
        this.type = type;
    }

    public static GiftCategory validateType(String category){
        try{
            return GiftCategory.valueOf(category);
        }catch(IllegalStateException e){
            //커스텀 전 임시
            throw new IllegalArgumentException();
        }
    }

    public static GiftCategory valueOf(Integer type){
        GiftCategory giftCategory = null;
        switch (type){
            case 1:
                giftCategory = GiftCategory.가벼운선물;
                break;
            case 2:
                giftCategory = GiftCategory.생일;
                break;
            case 3:
                giftCategory = GiftCategory.집들이;
                break;
            case 4:
                giftCategory = GiftCategory.시험;
                break;
            case 5:
                giftCategory = GiftCategory.승진;
                break;
            case 6:
                giftCategory = GiftCategory.졸업;
                break;
            case 7:
                giftCategory = GiftCategory.기타;
                break;
            default:
                break;
        }
        return giftCategory;
    }
}
