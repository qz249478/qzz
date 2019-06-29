package com.common.test;

/**
 * @author quanzhengzheng
 * @date 2019-04-16
 */
public class MainTest {

    /**
     * 增加获取字符串长度的方法
     * 中文字符长度为2,其他为1
     * @param value 字符串
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static void main(String[] args) {
        String reg1 = "^[a-z|A-Z][a-z|A-Z|.|·| |\\s]*[a-z|A-Z]$";
        String reg2 = "^[\u4E00-\u9FA5][\u4E00-\u9FA5|.|·| |\\s]*[\u4E00-\u9FA5]$";
        String appntName = "JI-EYING CHEN";
        int tNameLength = chineseLength(appntName);
        String tempName = appntName.replaceAll("\\.","").replaceAll("·","").replaceAll(" ","");
        if(!((appntName.matches(reg1) && tempName.length()>=4) ||appntName.matches(reg2))|| tNameLength<4 ||tNameLength>40)
        {
            System.out.print("true");
        }else {
            System.out.print("false");
        }
    }
}
