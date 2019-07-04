package com.common.util.convert;

import com.logger.Log4jKit;

public class binaryConvert extends Log4jKit {

//    private static char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static char[] array = new char[1633];
    public static String myDec(int number, int n) {
        // String 是不可变的，每次改变都要新建一个Strng，很浪费时间。
        // StringBuilder是可变的String
         StringBuilder result = new StringBuilder();
        // 模拟计算进制的过程
         while (number > 0) {
             result.insert(0, array[number % n]);
             number /= n;
         }
         info("转换后的二进制" + result.toString());
         return result.toString();
    }

    public static void main(String[] args) {
        new binaryConvert().myDec(3,2);
    }
}
