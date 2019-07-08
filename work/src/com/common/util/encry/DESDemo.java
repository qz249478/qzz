package com.common.util.encry;

import com.common.util.encry.cipher.Hex;
import com.common.util.security.ConvertKey;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.net.URLEncoder;
import java.security.Key;

/**
 * @author quanzhengzheng
 * @date  2019-06-12
 * @Description DES加解密
 * <p>DES算是发明最早的最广泛使用的分组对称加密算法，
 * 其全程是Data Encryption Standard，它需要三个参数来完成加解密的工作，
 * 分别是Key、Data以及Mode，其中Key是加密密钥（因为DES是一种对称加密算法，
 * 所以解密的密钥也是它了），Data是待加密或解密的数据，
 * Mode则用来指定到底是加密还是解密
 */
public class DESDemo {

    private static Cipher cipher = null;
    private static String testCode = "86110020190210056997";
    private static String testkey = "taobao20130121yeswecan";
    private static String testEnCode = "19576E8EB938228B";
    private static Logger logger = Logger.getLogger(DESDemo.class);

    /**
     * @Description  数据加密
     * @param   key 密钥
     * @param   code 需要加密的字符串
     * @return  desCode 加密后的字符串
     */
    private static String desCode(String code,String key) {
        logger.info("加密开始");
        String desCode = "";
        try {
            //密钥转换
            Key k = ConvertKey.convertSecretKey(key);
            //Cipher对象实际完成加密操作
            cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE,k);
            //正式执行加密操作，获取加密结果desCodeResult
            byte[] desCodeResult = cipher.doFinal(code.getBytes("UTF-8"));
            //System.out.println("DESEncode :" + Hex.toHexString(encodeResult));
             /*DES加密之后总会产生乱码，迫不得已用 BASE64 再包一层。但还是会产生像 “+”
             什么的字符，这些字符在某些浏览器上会被屏蔽。只得再 URLDecode一下*/
            desCode = URLEncoder.encode(Hex.parseByte2HexStr(desCodeResult),"UTF-8");
            logger.info("加密后的字符串desCode " + desCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("加密结束");
        return desCode;
    }

    /**
     * @Description 解密方法
     * @param code 需要解密的字符串
     * @param key 密钥
     * @return enCode 解密后的字符串
     */
    private static String desEnCode(String code, String key) {
        logger.info("desEnCode 解密开始");
        String enCode = "";
        try {
            Key k = ConvertKey.convertSecretKey(key);
            //Cipher对象实际完成加密操作
            cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE,k);
            //正式进入加密，并返回加密后的二进制数组
            byte[] DecodeResult = cipher.doFinal(Hex.parseHexStr2Byte(URLEncoder.encode(code,"UTF-8")));
            enCode = new String(DecodeResult);
            logger.info("解密后的字符串enCode " + enCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("desEnCode 解密结束");
        return enCode;
    }



   /* public static void bcDES (){
        try {

            //使用BouncyCastle 的DES加密
            Security.addProvider(new BouncyCastleProvider());

            //生成密钥Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES","BC");
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            //KEY转换
            DESKeySpec deSedeKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] encodeResult = cipher.doFinal(DESDemo.src.getBytes());
//            System.out.println("DESEncode :" + Hex.toHexString(encodeResult));

            //解密
            cipher.init(Cipher.DECRYPT_MODE,convertSecretKey);
            byte[] DecodeResult = cipher.doFinal(encodeResult);
            System.out.println("DESDncode :" + new String (DecodeResult));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public static void main(String[] args) {
        desCode(testCode,testkey);
//        desEnCode(testEnCode,testkey);
    }

}
