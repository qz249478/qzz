package com.common.util;

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
 * @Description 加解密工具类
 *
 */
public class hex {

    /**
     * @Description 二进制转化成十六进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * @Description 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * @Description 生成密钥Key
     */
    public static byte[] creteKey() {
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
     * @return
     */
    public static Key convertSecretKey() {
        return convertSecretKey();
    }

    /**
     * @Description KEY转换
     * @param key
     * @return
     */
    public static Key convertSecretKey(String key) {
        DESKeySpec deSadeKeySpec ;
        Key convertSecretKey = null;
        try {
            deSadeKeySpec = new DESKeySpec((new BASE64Decoder()).decodeBuffer(key));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            convertSecretKey = factory.generateSecret(deSadeKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertSecretKey;
    }


}
