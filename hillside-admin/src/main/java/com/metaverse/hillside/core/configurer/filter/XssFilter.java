package com.metaverse.hillside.core.configurer.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("XssFilter,init.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("XssFilter,destroy.");
    }
}
