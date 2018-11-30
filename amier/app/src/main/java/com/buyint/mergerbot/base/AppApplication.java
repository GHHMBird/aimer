package com.buyint.mergerbot.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.Utility.HttpsUtils;
import com.buyint.mergerbot.stroage.DataCache;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.iflytek.cloud.SpeechUtility;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/*
 * Created by test on 2017/9/27.
 */

public class AppApplication extends Application {

    public static Context context;

    public boolean translate = false;
    public static String language;
    public static AppApplication instans;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            MaterialHeader materialHeader = new MaterialHeader(context);
            materialHeader.setColorSchemeResources(R.color.color_cccccc);
            return materialHeader;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context).setDrawableSize(20));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ZXingLibrary.initDisplayOpinion(this);

        //百度统计初始化
        StatService.start(this);

        //初始化极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //glide 加载https初始化
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));

        language = PreferencesKeeper.getString(this, getString(R.string.LANGUAGE));
        if (TextUtils.isEmpty(language)) {
            language = "zh";
        }

        DataCache.instance.init(this);
        context = getApplicationContext();

        // initializes the Iflytek voice recognition SDK.语音识别SDK初始化
        SpeechUtility.createUtility(AppApplication.this, "appid=" + getString(R.string.app_id));

        //高德初始化
        AMapLocationClientOption locationClint = new AMapLocationClientOption();
        locationClint.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClint.setOnceLocation(false);
        locationClint.setInterval(1000 * 60);
        locationClint.setLocationCacheEnable(false);
        AMapLocationClient amc = new AMapLocationClient(this);
        amc.setLocationOption(locationClint);
        amc.setLocationListener(result -> {
            if (result.getErrorCode() == 0) {
                PreferencesKeeper.putString(this, "city_name", result.getCity());
            } else {
                Log.e("hhm", "高德定位报错:" + result.getErrorCode() + result.getErrorInfo());
            }
        });
        amc.startLocation();
    }

    public static int getMainColor() {
        if (BaseActivity.themeColor == 1) {
            return 0xff232323;
        } else if (BaseActivity.themeColor == 2) {
            return 0xff3986ff;
        } else if (BaseActivity.themeColor == 3) {
            return 0xffb51818;
        } else if (BaseActivity.themeColor == 4) {
            return 0xff5a79ff;
        } else if (BaseActivity.themeColor == 5) {
            return 0xff2702bb;
        } else if (BaseActivity.themeColor == 6) {
            return 0xff7345ef;
        } else {
            return 0;
        }
    }

    /**
     * 获取OkHttpClient
     * 设置允许HTTPS
     */
    public static OkHttpClient getOkHttpClient(InputStream... certificates) {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates, null, null);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder = builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }

    /**
     * @param string -- checking text
     * @return
     */
    // 检测一句话是不是中文
    public static boolean checkIfStringIsChinese(String string) {
        for (char character : string.toCharArray()) {
            if (Character.UnicodeBlock.of(character) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                return true;
            }
        }
        return false;
    }

    // 计算两个坐标点之间的距离
    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    //添加activity入栈
    public AppApplication() {

    }

    public static AppApplication getAppApplication() {
        if (instans == null) {
            instans = new AppApplication();
        }
        return instans;
    }

    private List<Activity> list_activity = new ArrayList<>();

    public void addActivity(Activity activity) {
        list_activity.add(activity);
    }

    public void cleanActivity() {
        for (Activity activity : list_activity) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
