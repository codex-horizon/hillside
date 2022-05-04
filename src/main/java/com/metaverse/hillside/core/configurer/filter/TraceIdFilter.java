package com.metaverse.hillside.core.configurer.filter;

import com.metaverse.hillside.common.utils.CommonUtil;
import com.metaverse.hillside.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TraceIdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TraceIdFilter,init.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 告知Servlet用UTF-8转码，而非用默认的ISO-8859-1
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 生成 跟踪ID
        response.setHeader(Constants.TRACE_ID, CommonUtil.generateUUID());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("TraceIdFilter,destroy.");
    }
}
