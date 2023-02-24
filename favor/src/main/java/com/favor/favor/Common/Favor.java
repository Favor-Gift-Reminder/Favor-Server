package com.favor.favor.Common;

import lombok.Getter;

public enum Favor {
//    심플한, 실용성있는, 귀여운, 정성이담긴, 오래쓰는, 가성비좋은, 감성있는, 맛있는, 가격있는, 쓸데없는, 아기자기한, 독특한, 힙한
    심플한(1),
    실용성있는(2),
    귀여운(3),
    정성이담긴(4),
    오래쓰는(5),
    가성비좋은(6),
    감성있는(7),
    건강에좋은(8),
    힙한(9),
    양보단질(10),
    맛있는(11),
    가격있는(12),
    쓸데없는(13),
    아기자기한(14),
    위로가되는(15),
    독특한(16);

    @Getter
    Integer type;
    Favor(Integer type){
        this.type = type;
    }

    public static Favor valueOf(Integer type){
        Favor favor = null;
        switch (type) {
            case 1:
                favor = Favor.심플한;
                break;
            case 2:
                favor = Favor.실용성있는;
                break;
            case 3:
                favor = Favor.귀여운;
                break;
            case 4:
                favor = Favor.정성이담긴;
                break;
            case 5:
                favor = Favor.오래쓰는;
                break;
            case 6:
                favor = Favor.가성비좋은;
                break;
            case 7:
                favor = Favor.감성있는;
                break;
            case 8:
                favor = Favor.건강에좋은;
                break;
            case 9:
                favor = Favor.힙한;
                break;
            case 10:
                favor = Favor.양보단질;
                break;
            case 11:
                favor = Favor.맛있는;
                break;
            case 12:
                favor = Favor.가격있는;
                break;
            case 13:
                favor = Favor.쓸데없는;
                break;
            case 14:
                favor = Favor.아기자기한;
                break;
            case 15:
                favor = Favor.위로가되는;
                break;
            case 16:
                favor = Favor.독특한;
                break;
            default:
                break;
        }
        return favor;
    }
}
