package com.metaverse.hillside.common.restful.response;

public interface IResult<T> {

    String getCode();

    String getMessage();

    T getData();
}
