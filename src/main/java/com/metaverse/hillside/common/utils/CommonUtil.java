package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.exception.BusinessException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CommonUtil {

    /**
     * 生成 uuid
     *
     * @return 返回 uuid
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取 TraceId，从 Request 对象中
     *
     * @return 返回 traceId
     */
    public static String getTraceIdByRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(servletRequestAttributes)) {
            throw new BusinessException("getTraceIdByRequest() By servletRequestAttributes is null");
        }
        HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
        if (ObjectUtils.isEmpty(httpServletResponse)) {
            throw new BusinessException("getTraceIdByRequest() By httpServletResponse is null");
        }
        return httpServletResponse.getHeader(Constants.TRACE_ID);
    }


}
