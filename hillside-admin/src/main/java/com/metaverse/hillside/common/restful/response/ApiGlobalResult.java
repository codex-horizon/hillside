package com.metaverse.hillside.common.restful.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiGlobalResult implements IGlobalResponse, Serializable {

    private String applicationName;

    private String traceId;

    private Object body;

}
