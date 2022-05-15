package com.metaverse.hillside.common.restful.response;


import lombok.Data;

import java.io.Serializable;

@Data
public class ApiPageable<T> implements IPageable<T>, Serializable {

    /* 响应总数 */
    private long total;

    /* 响应列表 */
    private T list;

    private ApiPageable(final Long total, final T list) {
        this.total = total;
        this.list = list;
    }

    public static <T> ApiPageable<T> response(final Long total, final T list) {
        return new ApiPageable<>(total, list);
    }

}
