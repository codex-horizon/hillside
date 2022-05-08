package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class CommonUtil {

    /**
     * 生成 UUID
     *
     * @return 返回 UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取 TraceId，从 Response 对象中
     *
     * @return 返回 TraceId
     */
    public static String getTraceIdByRequest() {
        return getHttpServletResponse().getHeader(Constants.TRACE_ID);
    }

    /**
     * 获取 HttpServletRequest 对象
     *
     * @return 返回 HttpServletRequest 对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest httpServletRequest = getServletRequestAttributes().getRequest();
        if (ObjectUtils.isEmpty(httpServletRequest)) {
            throw new BusinessException("获取 httpServletRequest 空");
        }
        return httpServletRequest;
    }

    /**
     * 获取 HttpServletResponse 对象
     *
     * @return 返回 HttpServletResponse 对象
     */
    public static HttpServletResponse getHttpServletResponse() {
        HttpServletResponse httpServletResponse = getServletRequestAttributes().getResponse();
        if (ObjectUtils.isEmpty(httpServletResponse)) {
            throw new BusinessException("获取 httpServletResponse 空");
        }
        return httpServletResponse;
    }

    /**
     * 获取 ServletRequestAttributes 对象
     *
     * @return 返回 ServletRequestAttributes 对象
     */
    private static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(servletRequestAttributes)) {
            throw new BusinessException("获取 servletRequestAttributes 空");
        }
        return servletRequestAttributes;
    }
}
