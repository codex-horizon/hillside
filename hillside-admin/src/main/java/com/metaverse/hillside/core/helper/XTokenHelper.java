package com.metaverse.hillside.core.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.metaverse.hillside.core.env.EnvProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static sun.security.x509.CertificateIssuerExtension.ISSUER;

@Slf4j
@Component
public class XTokenHelper {

    private final EnvProperties envProperties;

    XTokenHelper(final EnvProperties envProperties) {
        this.envProperties = envProperties;
    }

    /**
     * 生成 X-Token
     *
     * @param payload 有效载荷信息
     * @return 返回 X-Token
     */
    public String generateXToken(Map<String, String> payload) {
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

        // 构建并设置指定X-Token过期时间为1天、签名算法
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return builder
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(envProperties.getJwtSignatureSecretKey()));
    }

    /**
     * 解码 X-Token
     *
     * @param xToken                X-Token
     * @param jwtSignatureSecretKey X-Token签名密钥
     * @return 返回 解码 对象
     */
    private DecodedJWT decodeXToken(String xToken, String jwtSignatureSecretKey) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSignatureSecretKey)).build();
        return jwtVerifier.verify(xToken);
    }

    /**
     * 获取 Claims
     *
     * @param xToken                X-Token
     * @param jwtSignatureSecretKey X-Token签名密钥
     * @return 返回 Claims 对象
     */
    public Map<String, Claim> getClaims(String xToken, String jwtSignatureSecretKey) {
        DecodedJWT decodedJWT = decodeXToken(xToken, jwtSignatureSecretKey);
        return decodedJWT.getClaims();
    }
}