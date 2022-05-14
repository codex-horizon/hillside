package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum  AccountStateEnum {

    DISABLED(0, "禁用"),
    ENABLE(1, "启用"),
    ;

    private final Integer code;

    private final String desc;

    AccountStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
