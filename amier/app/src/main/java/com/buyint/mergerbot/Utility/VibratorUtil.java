package com.buyint.mergerbot.Utility;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by CXC on 2018/4/20
 */

public class VibratorUtil {
    public static void vibrate(Context context, long time) {
        Vibrator v = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        v.vibrate(time);
    }

    public static void vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator v = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        v.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
