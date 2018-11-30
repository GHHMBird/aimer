package com.buyint.mergerbot.Utility;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by CXC on 2018/4/8
 */

public class StringUtils {

    public static String numberAddDouHao(double d){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(d);
    }

    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return "";
        }
    }

    public static String getDBPath() {
        String sdCardPath = getSDPath();
        if (TextUtils.isEmpty(sdCardPath)) {
            return "";
        } else {
            return sdCardPath + File.separator + "GreenDao" + File.separator + "sqlite";
        }
    }

    public static String toString(String str) {
        //        String encodeData = encodeToHex(str);
        String decodeData = decodeFromHex(str);
        return decodeData;
    }

    public static String decodeFromHex(String encodeData) {
        try {
            byte[] decodeData = fromHex(encodeData);
            return new String(decodeData, "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] fromHex(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
