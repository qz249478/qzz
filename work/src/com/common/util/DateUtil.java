package com.common.util;

import com.logger.Log4jKit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends Log4jKit {


    // 继承Log4jKit，以后就不用每个类都写一遍Logger.getLogger
//    private static Logger logger = Logger.getLogger(DateUtil.class);
    public static void main(String[] args) {
        info("");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
    }
}
