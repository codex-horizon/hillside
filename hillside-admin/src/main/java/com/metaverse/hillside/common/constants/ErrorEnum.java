package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    X_TOKEN_SIGNATURE_VERIFICATION("X_TOKEN_SIGNATURE_VERIFICATION","X-Token 无效签名"),
    X_TOKEN_EXPIRED("X_TOKEN_EXPIRED","X-Token 已经过期"),
    X_TOKEN_ALGORITHM_MISMATCH("X_TOKEN_ALGORITHM_MISMATCH","X-Token 算法不一致"),
    X_TOKEN_Blank("X_TOKEN_Blank","X-Token 空"),

    ;

    private final String code;

    private final String desc;

    ErrorEnum(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

}
