package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum DeleteStatusEnum {

    DELETED(0, "删除"),
    NOT_DELETED(1, "未删除"),

    ;

    private final Integer code;

    private final String desc;

    DeleteStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}