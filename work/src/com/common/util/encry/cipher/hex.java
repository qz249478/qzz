package com.common.util.encry.cipher;

/**
 * @author  quanzhengzheng
 * @date    2019-06-12
 * @Description 字节码转换工具类
 *
 */
public class hex {

    /**
     * @Description     二进制转化成十六进制
     * @param buffer    需要转换的二进制数组
     * @return sb       转换后的16进制字符串
     */
    public static String parseByte2HexStr(byte buffer[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buffer.length; i++) {
            String hex = Integer.toHexString(buffer[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * @Description     将16进制转换为二进制
     * @param  hexStr   需要转换的16进制字符串
     * @return result   转换后的二进制数组
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

}
