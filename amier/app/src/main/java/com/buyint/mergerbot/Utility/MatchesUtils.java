package com.buyint.mergerbot.Utility;

import android.text.TextUtils;

/**
 * Created by CXC on 2018/4/2
 */

public class MatchesUtils {

    /**
     * 检测手机号是否合法
     *
     * @param mobiles
     * @return
     */
    public static boolean validatePhoneNumber(String mobiles) {
        String regrx = "^((13[0-9])|(15[^4])|(16[0-9])|(18[0-9])|17[0-8]|(147,145))\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(regrx);
    }

    /**
     * 检测邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        String regrx = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return !TextUtils.isEmpty(email) && email.matches(regrx);
    }

}
