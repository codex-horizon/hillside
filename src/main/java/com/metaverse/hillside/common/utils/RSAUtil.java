package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态获取：外部获取公钥，解密时候，也要带回来。使用完成后，移除。
 */
@Slf4j
public class RSAUtil {

    // 私钥字符串
    private final static Map<String, String> keyCache = new HashMap<>();

    public static String getPublicKey() {
        return initKey();
    }

    /**
     * 获取公钥私钥
     */
    public static String initKey() {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥字符串|公钥字符串
            String publicKeyStr = new String(encoder.encode((privateKey.getEncoded())));
            keyCache.put(publicKeyStr, new String(encoder.encode(publicKey.getEncoded())));
            return publicKeyStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException("获取公钥|私钥 失败");
        }

    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     */
    public static String encrypt(String str, String publicKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            Base64.Encoder encoder = Base64.getEncoder();
            //base64编码的公钥
            byte[] decoded = decoder.decode(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return encoder.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8.name())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("RSA公钥加密 失败");
        }
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     */
    public static String decrypt(String str, String publicKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            // 64位解码加密后的字符串
            byte[] inputByte = decoder.decode(str.getBytes(StandardCharsets.UTF_8.name()));
            // base64编码的私钥
            byte[] decoded = decoder.decode(publicKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("RSA私钥解密 失败");
        }
    }
}
