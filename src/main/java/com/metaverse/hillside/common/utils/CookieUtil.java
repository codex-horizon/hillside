package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CookieUtil {

    public static void addPublicKeyByCookie(String publicKey) {
        // 添加Cookie至Response
        Cookie cookie = new Cookie(Constants.PUBLIC_KEY, CommonUtil.toHex(publicKey.getBytes(StandardCharsets.UTF_8)));
        cookie.setMaxAge(60); // 有效60秒
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setVersion(0);
        cookie.setHttpOnly(true);
        CommonUtil.getHttpServletResponse().addCookie(cookie);
    }

    public static String getPublicKeyByCookie() {
        Cookie[] cookies = CommonUtil.getHttpServletRequest().getCookies();
        String publicKey = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.PUBLIC_KEY)) {
                publicKey = cookie.getValue();
                break;
            }
        }
        if (!StringUtils.hasLength(publicKey)) {
            throw new BusinessException("客户端Cookie未启用");
        }
        return CommonUtil.hexString(publicKey);
    }
}
