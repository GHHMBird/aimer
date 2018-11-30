package com.buyint.mergerbot.Utility;

import android.util.Log;

/**
 * Created by CXC on 2018/2/1.
 *  Log Util
 */

public class LogUtil {

    public static final String TAG = "hhm";

    public static void info(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void info( String msg) {
        Log.i(TAG, msg);
    }

    public static void debug(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void debug( String msg) {
        Log.i(TAG, msg);
    }

    public static void error(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void error( String msg) {
        Log.i(TAG, msg);
    }

}
