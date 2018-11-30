package com.buyint.mergerbot.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnBaseCallback;

/**
 * Created by huheming on 2018/9/19
 */

public class OnTopCallBack extends OnBaseCallback {

    private Context context;

    public OnTopCallBack(Context context) {
        this.context = context;
    }

    public OnTopCallBack(Context context, float offset) {
        super(offset);
        this.context = context;
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {

        //获取屏幕宽高
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;

        marginInfo.rightMargin = 10;
        marginInfo.bottomMargin = 10;
        marginInfo.topMargin = dp2px(70f);
    }

    public int dp2px(Float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()));
    }
}
