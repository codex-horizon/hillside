package com.metaverse.hillside.common.restful.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pageable implements Serializable {

    // 起始页
    private int startPage = 0;

    // 每页显示数量
    private int pageSize = 10;

    public void setStartPage(int startPage) {
        if (startPage > 0) {
            startPage--;
        }
        this.startPage = startPage;
    }

}
