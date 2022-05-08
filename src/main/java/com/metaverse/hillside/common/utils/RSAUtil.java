package com.metaverse.hillside.common.utils;

//import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

    public static Map<String, String> createKeys() {
        // 为RSA算法创建一个KeyPairGenerator对象（KeyPairGenerator，密钥对生成器，用于生成公钥和私钥对）
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance( "RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(String.format("没有这样的算法[%s]",  "RSA"));
        }

        // 初始化KeyPairGenerator对象,密钥长度
        keyPairGenerator.initialize(1024);
        // 生成密匙对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        // 返回一个publicKey经过二次加密后的字符串
//        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        String publicKeyStr = Base64.getUrlEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        // 返回一个privateKey经过二次加密后的字符串
//        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        String privateKeyStr = Base64.getUrlEncoder().encodeToString(privateKey.getEncoded());

        return new HashMap<String, String>() {{
            put("publicKey", publicKeyStr);
            put("privateKey", privateKeyStr);
        }};
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance( "RSA");
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
//                Base64.decodeBase64(publicKey)
                Base64.getUrlDecoder().decode(publicKey)
        );
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance( "RSA");
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
//                Base64.decodeBase64(privateKey)
                Base64.getUrlDecoder().decode(privateKey)
        );
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     *
     * @param data      被加密数据
     * @param publicKey 公钥
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance( "RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8.name()), publicKey.getModulus().bitLength()));
            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8.name()), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance( "RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8.name()), privateKey.getModulus().bitLength()));
            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8.name()), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data       被加密数据
     * @param privateKey 私钥
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance( "RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), StandardCharsets.UTF_8.name());
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getUrlDecoder().decode(data), privateKey.getModulus().bitLength()), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance( "RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), StandardCharsets.UTF_8.name());
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getUrlDecoder().decode(data), publicKey.getModulus().bitLength()), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int decryptMode, byte[] dataByte, int keySize) {
        int maxBlock;
        if (decryptMode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        byte[] resultByteData;
        try {
            while (dataByte.length > offSet) {
                if (dataByte.length - offSet > maxBlock) {
                    buff = cipher.doFinal(dataByte, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(dataByte, offSet, dataByte.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }

            resultByteData = out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultByteData;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtil.createKeys();
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "寒山月初℃";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtil.publicEncrypt(str, RSAUtil.getPublicKey(publicKey));
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAUtil.privateDecrypt(encodedData, RSAUtil.getPrivateKey(privateKey));
        System.out.println("解密后文字: \r\n" + decodedData);
    }
}
