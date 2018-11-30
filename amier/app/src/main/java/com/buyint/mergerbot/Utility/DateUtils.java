package com.buyint.mergerbot.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by licheng  on date 2018/8/15
 */
public class DateUtils {
    public static String getdata(Long date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String Newdate = simpleDateFormat.format(new Date(date));
        return Newdate;
    }
}
