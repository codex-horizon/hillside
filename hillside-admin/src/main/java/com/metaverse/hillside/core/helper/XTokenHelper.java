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

@Slf4j
@Component
public class XTokenHelper {

    /**
     * Key：账户account，以后考虑优化
     * Value：X-Token字符串
     */
    public final static Map<String, String> X_TOKEN_POOLS = new HashMap<>();

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
        JWTCreator.Builder createJWT = JWT.create();
        // 构建Header
        createJWT.withHeader(new HashMap<String, Object>() {{
            put("owner", "auth0");
        }});

        // 设置签发主体
//        createJWT.withIssuer(ISSUER);

        // 构建Payload（自定义参数）
        payload.forEach(createJWT::withClaim);

        // 设置签发时间
        createJWT.withIssuedAt(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        // 构建并设置指定X-Token过期时间为1天、签名算法
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return createJWT
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
