package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.constants.ErrorEnum;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.exception.ThrowException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertUtil {

    public static ThrowException isTrue(boolean expression, ErrorEnum errorEnum) {
        return (message) -> {
            if (expression) {
                log.error("断言错误信息：{} {}", errorEnum.getCode(), errorEnum.getDesc());
                throw new BusinessException(errorEnum.getDesc());
            }
        };
    }

}
