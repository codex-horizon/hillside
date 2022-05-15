package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum RestfulStatusEnum {

    SUCCEED("SUCCEED", "成功"),
    FAILED("FAILED", "失败"),
    WARNED("WARNED", "警告"),
    NOT_AUTHENTICATION("NOT_AUTHENTICATION", "未认证"),

    ;

    private final String code;

    private final String desc;

    RestfulStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}