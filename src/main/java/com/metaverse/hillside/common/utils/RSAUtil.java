package com.metaverse.hillside.common.utils;

import com.metaverse.hillside.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(512, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 私钥字符串、公钥字符串
            String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
            keyCache.put(publicKeyStr, new String(Base64.encodeBase64(privateKey.getEncoded())));
            return publicKeyStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException("初始化密钥 失败");
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
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
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
        String privateKey = keyCache.get(publicKey);
        try {
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            // 64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            // base64编码的私钥
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("RSA私钥解密 失败");
        }
    }

    public static void main(String[] args) {
        String publicKey = RSAUtil.getPublicKey();
        log.info("publicKey:[{}]", publicKey);
        String account = encrypt("13221811969", publicKey);
        String password = encrypt("Wubc0229()", publicKey);
        System.out.println(decrypt(account, publicKey));
        System.out.println(decrypt(password, publicKey));
    }
}
