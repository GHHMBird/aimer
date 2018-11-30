package com.buyint.mergerbot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import java.lang.reflect.Field;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/*
 * Created by test on 2017/9/28.
 */

public class CustomVerticalViewPager extends VerticalViewPager {

    private int maxShowPage = 0;
    private float y;

    public CustomVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    private void postInitViewPager() {
        try {
            Class<?> viewpager = VerticalViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            ScrollCustomDuration mScroller = new ScrollCustomDuration(getContext(), (Interpolator) interpolator.get(null));
            mScroller.setmScrollFactor(2000);
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
