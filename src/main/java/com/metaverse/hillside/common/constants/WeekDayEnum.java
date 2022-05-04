package com.metaverse.hillside.common.constants;

import lombok.Getter;

@Getter
public enum WeekDayEnum {

    MON(1, "星期一"),
    TUE(2, "星期二"),
    WED(3, "星期三"),
    THU(4, "星期四"),
    FRI(5, "星期五"),
    SAT(6, "星期六"),
    SUN(7, "星期日"),

    ;

    private final Integer code;

    private final String desc;

    WeekDayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
