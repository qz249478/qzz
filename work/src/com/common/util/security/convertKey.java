package com.common.util.security;

import sun.misc.BASE64Decoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author quanzhengzheng
 * @date 2019-06-12
 * @Description 秘钥转换工具类
 *
 */
public class convertKey {

    /**
     * @Description 生成密钥Key
     */
    private static byte[] creteKey() {
        KeyGenerator keyGenerator = null;
        byte[] bytesKey ;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        bytesKey = secretKey.getEncoded();
        return bytesKey;
    }

    /**
     * @Description KEY转换
     * @param key
     * @return convertSecretKey
     */
    public static Key convertSecretKey(String key) {
        DESKeySpec deSadeKeySpec ;
        Key convertSecretKey = null;
        try {
            deSadeKeySpec = new DESKeySpec((new BASE64Decoder()).decodeBuffer(key));
            //创建一个密匙工厂
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            //密钥转换
            convertSecretKey = factory.generateSecret(deSadeKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertSecretKey;
    }
}
