package com.buyint.mergerbot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.VideoView;

/**
 * Created by CXC on 2018/4/9
 */

public class MyViewoView extends VideoView {

    public MyViewoView(Context context) {
        super(context);
    }

    public MyViewoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getWidth(), widthMeasureSpec);
        int height = getDefaultSize(getHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
