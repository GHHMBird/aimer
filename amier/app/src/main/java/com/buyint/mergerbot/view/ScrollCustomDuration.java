package com.buyint.mergerbot.view;


import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ScrollCustomDuration extends Scroller {
    private int mScrollFactor = 600;

    public ScrollCustomDuration(Context context) {
        super(context);
    }

    public ScrollCustomDuration(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ScrollCustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollFactor);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollFactor);
    }

    public void setmScrollFactor(int mScrollFactor) {
        this.mScrollFactor = mScrollFactor;
    }
}
