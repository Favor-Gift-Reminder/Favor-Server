package com.favor.favor.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Category {
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
    Category (Integer type){
        this.type = type;
    }

    public static Category valueOf(Integer type){
        Category category = null;
        switch (type){
            case 1:
                category = Category.가벼운선물;
                break;
            case 2:
                category = Category.생일;
                break;
            case 3:
                category = Category.집들이;
                break;
            case 4:
                category = Category.시험;
                break;
            case 5:
                category = Category.승진;
                break;
            case 6:
                category = Category.졸업;
                break;
            case 7:
                category = Category.기타;
                break;
            default:
                break;
        }
        return category;
    }
}
