package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum AccountCategoryEnum {

    ORDINARY_USER(0, "普通账户"),
    PLATFORM_USER(1, "平台账户"),
    ;

    private final Integer code;

    private final String desc;

    AccountCategoryEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
