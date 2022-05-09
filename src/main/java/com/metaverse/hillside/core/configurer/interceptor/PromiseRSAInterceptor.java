package com.metaverse.hillside.core.configurer.interceptor;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Slf4j
public class PromiseRSAInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ResponseCookie cookie = ResponseCookie.from(Constants.RSA_PUBLIC_KEY, RSAUtil.getPublicKey())
                // 禁止js读取
                .httpOnly(true)
                // 在http下也传输
                .secure(true)
                // 域名
                .domain("localhost")
                .path("/")
                // 1个小时候过期
                .maxAge(Duration.ofHours(1))
                // 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                .sameSite("None")
                .build();
        // 设置Cookie Header 使用andHeader能设置多个，setHeader只能设置一个Set-Cookie
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
