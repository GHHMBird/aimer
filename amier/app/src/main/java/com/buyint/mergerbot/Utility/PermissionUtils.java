package com.buyint.mergerbot.Utility;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.buyint.mergerbot.base.AppApplication;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/5/24.
 */

public class PermissionUtils {

    public static String[] hasPermission(String... permissions) {
        ArrayList<String> noPermission = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(AppApplication.context, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                noPermission.add(permissions[i]);
        }
        return noPermission.toArray(new String[0]);
    }

}
