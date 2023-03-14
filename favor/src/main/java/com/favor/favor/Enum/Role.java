package com.favor.favor.Enum;

import lombok.Getter;

public enum Role {
//    ADMIN, USER

    ADMIN('A'),
    USER('U');

    @Getter
    Character type;
    Role(Character type){
        this.type = type;
    }

    public static Role valueOf(Character type){
        Role role = null;
        switch (type) {
            case 'A':
                role = Role.ADMIN;
                break;
            case 'U':
                role = Role.USER;
                break;
            default:
                break;
        }
        return role;
    }
}
