package com.buyint.mergerbot.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.Utility.DeviceUtils;
import com.buyint.mergerbot.bus.Bus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import static com.buyint.mergerbot.Utility.DeviceUtils.SYS_FLYME;
import static com.buyint.mergerbot.Utility.DeviceUtils.SYS_MIUI;

/**
 * Created by test on 2017/9/29.
 * keyboard base
 */

public class BaseActivity extends AppCompatActivity {

    public static float fontScale = 1.0f;
    public static int themeColor = 5;
    protected String system;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        system = DeviceUtils.getSystem();
        Bus.getDefault().register(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = fontScale;
        config.setLocale(new Locale(AppApplication.language));
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void setTitleWhiteAndTextBlank() {
        if (SYS_MIUI.equals(system)) {//小米
            //设置状态栏随布局变化 文字黑色
            setMyTitleColor(Color.TRANSPARENT, false);

            MIUISetStatusBarLightMode(getWindow(), true);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        } else if (SYS_FLYME.equals(system)) {//魅族
            //设置状态栏随布局变化 文字黑色
            setMyTitleColor(Color.TRANSPARENT, false);

            FlymeSetStatusBarLightMode(getWindow(), true);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置状态栏随布局变化 文字黑色
            setMyTitleColor(Color.TRANSPARENT, false);

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (themeColor == 1) {
            setTheme(R.style.black_theme);
        } else if (themeColor == 2) {
            setTheme(R.style.light_blue_theme);
        } else if (themeColor == 3) {
            setTheme(R.style.red_theme);
        } else if (themeColor == 4) {
            setTheme(R.style.deep_blue_theme);
        } else if (themeColor == 5) {
            setTheme(R.style.standard_theme);
        } else if (themeColor == 6) {
            setTheme(R.style.pink_theme);
        }
    }

    public void setMyTitleColor() {
        setMyTitleColor(themeColor, false);
    }

    /**
     * 沉浸式状态栏
     */
    public void setMyTitleColor(int intColor, boolean isStatusColorTransform) {
        if (intColor == 0) {
            //透明色 暂不作处理
        } else if (intColor == 1) {
            intColor = 0xff232323;
            setTheme(R.style.black_theme);
        } else if (intColor == 2) {
            intColor = 0xff3986ff;
            setTheme(R.style.light_blue_theme);
        } else if (intColor == 3) {
            intColor = 0xffb51818;
            setTheme(R.style.red_theme);
        } else if (intColor == 4) {
            intColor = 0xff5a79ff;
            setTheme(R.style.deep_blue_theme);
        } else if (intColor == 5) {
            intColor = 0xff2702bb;
            setTheme(R.style.standard_theme);
        } else if (intColor == 6) {
            intColor = 0xff7345ef;
            setTheme(R.style.pink_theme);
        }
        if (isStatusColorTransform) {
            intColor = 0;
        }
        View decorView = getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(options);
        getWindow().setStatusBarColor(intColor);
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("addExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /*
     * 全屏幕专用
     */
    public void splashTheme() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            // navigation bar height
            int navigationBarHeight = 0;
            int resourceId = getResources().getIdentifier(getString(R.string.navigation_bar_height), getString(R.string.dimen), getString(R.string.android));
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // status bar height
            int statusBarHeight = 0;
            resourceId = getResources().getIdentifier(getString(R.string.status_bar_height), getString(R.string.dimen), getString(R.string.android));
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // display window size for the app layout
            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            int keyboardHeight = rootLayout.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());

            if (keyboardHeight <= 0) {
                onHideKeyboard();
            } else {
                onShowKeyboard(keyboardHeight);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Bus.getDefault().unregister(this);

        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void showThrowable(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    public void showLoadingFragment(int viewId) {
        LoadingFragment mLoadingFragment = LoadingFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(viewId, mLoadingFragment, LoadingFragment.TAG)
                .commitAllowingStateLoss();
    }

    public void removeLoadingFragment() {
        LoadingFragment mLoadingFragment = findLoadingFragment();
        if (mLoadingFragment != null) {
            mLoadingFragment.removeSelf(getSupportFragmentManager());
        }
    }

    public void showNoDataView() {
        LoadingFragment mLoadingFragment = findLoadingFragment();
        if (mLoadingFragment != null) {
            mLoadingFragment.showNoDataView();
        }

    }

    public LoadingFragment findLoadingFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
        if (fragment != null) {
            return (LoadingFragment) fragment;
        }
        return null;
    }

    /*
     * 显示键盘
     */
    public void showkeybord(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /*
     * 隐藏键盘
     */
    public void hintkeybord(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    @Override
    public void finish() {
        Bus.getDefault().unregister(this);
        super.finish();
    }

    public void hideKeyBoard() {
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (input.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public int dp2px(Float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics()));
    }
}