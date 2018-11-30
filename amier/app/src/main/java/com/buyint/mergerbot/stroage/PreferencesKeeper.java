package com.buyint.mergerbot.stroage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/*
 * Created by fuliang on 2017/3/7.
 */

@SuppressLint("WrongConstant")
public class PreferencesKeeper {

    private static final String GESTURE_SHOW_STATE = "guest_show_state";
    private static final String ASSETS_MONEY = "assets_money";
    private static final String GESTURE_STATE = "guest_state";
    private static final String USER_POLICY = "user_about";
    private static final String HAS_NEW_FEATURE_SHOWN = "has_new_feature_show";

    public static void saveGestureHintStatus(Context context, String name, boolean key) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, key);
        editor.apply();
    }

    public static boolean getGestureHintStatus(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(name, false);
    }

    public static void saveGestureKey(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, key);
        editor.apply();
    }

    public static String getGestureKey(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref.getString(name, "");
    }

    public static void setHasNewFeatureShown(Context context) {
        SharedPreferences pref = context.getSharedPreferences(USER_POLICY, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(HAS_NEW_FEATURE_SHOWN, true);
        editor.apply();
    }

    public static boolean getHasNewFeatureShown(Context context) {
        SharedPreferences pref = context.getSharedPreferences(USER_POLICY, Context.MODE_APPEND);
        return pref != null && pref.getBoolean(HAS_NEW_FEATURE_SHOWN, false);
    }

    public static void saveGuestState(Context context, String name, boolean key) {
        SharedPreferences pref = context.getSharedPreferences(GESTURE_STATE + name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(GESTURE_STATE + name, key);
        editor.apply();
    }

    public static boolean getGuestState(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(GESTURE_STATE + name, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(GESTURE_STATE + name, false);
    }

    public static void saveGuestShowState(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(GESTURE_SHOW_STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(GESTURE_SHOW_STATE + name, true);
        editor.apply();
    }

    public static boolean getGuestShowState(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(GESTURE_SHOW_STATE, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(GESTURE_SHOW_STATE + name, false);
    }

    public static void clearData(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        editor.apply();
    }

    public static void saveAssetsMoneyShow(Context context, String name, boolean isShow) {
        SharedPreferences pref = context.getSharedPreferences(ASSETS_MONEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ASSETS_MONEY + name, isShow);
        editor.apply();
    }

    public static boolean getAssetsMoneyShow(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(ASSETS_MONEY, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(ASSETS_MONEY + name, false);
    }

    public static void putBoolean(Context context, String name, boolean b) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, b);
        editor.apply();
    }

    public static void putInt(Context context, String name, int i) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, i);
        editor.apply();
    }

    public static int getInt(Context context, String name, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        if (pref == null) {
            return defaultValue;
        } else {
            return pref.getInt(name, defaultValue);
        }
    }

    public static boolean getFalseBoolean(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(name, false);
    }

    public static boolean getTrueBoolean(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref != null && pref.getBoolean(name, true);
    }

    public static void putString(Context context, String name, String value) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getString(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        if (pref == null) {
            return "";
        } else {
            return pref.getString(name, "");
        }
    }



}
