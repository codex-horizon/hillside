package com.metaverse.hillside.common.restful.response;

public interface IPageable<T> {

    long getTotal();

    T getList();
}
