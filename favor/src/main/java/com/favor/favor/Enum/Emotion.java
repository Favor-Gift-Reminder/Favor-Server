package com.favor.favor.Enum;

import lombok.Getter;

public enum Emotion {
//    감동이에요, 기뻐요, 좋아요, 그냥그래요, 별로에요
    감동이에요(1),
    기뻐요(2),
    좋아요(3),
    그냥그래요(4),
    별로에요(5);

    @Getter
    Integer type;
    Emotion(Integer type){
        this.type = type;
    }

    public static Emotion valueOf(Integer type){
        Emotion emotion = null;
        switch (type) {
            case 1:
                emotion = Emotion.감동이에요;
                break;
            case 2:
                emotion = Emotion.기뻐요;
                break;
            case 3:
                emotion = Emotion.좋아요;
                break;
            case 4:
                emotion = Emotion.그냥그래요;
                break;
            case 5:
                emotion = Emotion.별로에요;
                break;
            default:
                break;
        }
        return emotion;
    }
}
