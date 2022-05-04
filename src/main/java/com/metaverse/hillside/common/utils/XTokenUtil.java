package com.metaverse.hillside.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static sun.security.x509.CertificateIssuerExtension.ISSUER;

/**
 * https://mp.weixin.qq.com/s/cd3PLLHA80HVS3STUf2ygw
 */
public class XTokenUtil {

    /**
     * 生成 X-Token
     *
     * @param payload 有效载荷信息
     * @return 返回 X-Token
     */
    public static String generateXToken(Map<String, String> payload, String jwtSignatureSecretKey) {
        JWTCreator.Builder builder = JWT.create();
        // 构建Header
        builder.withHeader(new HashMap<String, Object>() {{
            put("owner", "auth0");
        }});

        // 设置签发主体
        builder.withIssuer(ISSUER);

        // 构建Payload（自定义参数）
        payload.forEach(builder::withClaim);

        // 设置签发时间
        builder.withIssuedAt(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        // 构建并设置指定X-Token过期时间为7天、签名算法
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return builder
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(jwtSignatureSecretKey));
    }

    /**
     * 解码 X-Token
     *
     * @param xToken                X-Token
     * @param jwtSignatureSecretKey X-Token签名密钥
     * @return 返回 解码 对象
     */
    public static DecodedJWT decodeXToken(String xToken, String jwtSignatureSecretKey) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSignatureSecretKey)).build();
        return jwtVerifier.verify(xToken);
    }
}
