package com.metaverse.hillside.common.constants;

import lombok.Getter;

/**
 * 之所以定义错误枚举，是因为前后端分离情况下，提示给前端，前端语义化后提示用户。
 */
@Getter
public enum ErrorEnum {

    X_TOKEN_SIGNATURE_VERIFICATION("X_TOKEN_SIGNATURE_VERIFICATION", "X-Token 无效签名"),
    X_TOKEN_EXPIRED("X_TOKEN_EXPIRED", "X-Token 已经过期"),
    X_TOKEN_ALGORITHM_MISMATCH("X_TOKEN_ALGORITHM_MISMATCH", "X-Token 算法不一致"),
    X_TOKEN_BLANK("X_TOKEN_BLANK", "X-Token 空"),

    ;

    private final String code;

    private final String desc;

    ErrorEnum(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

}
