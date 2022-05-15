package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum GenderEnum {

    FEMALE(0, "女性"),
    MALE(1, "男性"),
    ;

    private final Integer code;

    private final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}