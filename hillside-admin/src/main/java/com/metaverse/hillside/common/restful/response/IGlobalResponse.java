package com.metaverse.hillside.common.restful.response;

public interface IGlobalResponse {

    String getApplicationName();

    void setApplicationName(String applicationName);

    String getTraceId();

    void setTraceId(String seqNo);

    Object getBody();

    void setBody(Object o);

}
