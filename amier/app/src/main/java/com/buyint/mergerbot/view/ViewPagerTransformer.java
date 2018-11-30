package com.buyint.mergerbot.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

/**
 * Created by CXC on 2018/3/30
 */

public class ViewPagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_ALPHA = 0.0f;//最小透明度
    private int nowPosition = 0;
    private ViewPagerTransnfornnmnerLnistneer listener;
//    private TextView tv;

    public void setCurrentItem(int nowPosition, TextView tv, ViewPagerTransnfornnmnerLnistneer listener) {
        this.nowPosition = nowPosition;
//        this.tv = tv;
        this.listener = listener;
    }

    public void setCurrentItem(int nowPosition) {
        this.nowPosition = nowPosition;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageHeight = view.getHeight();//得到view高

        if (position > 0 && position < 1) {
            float alphaFactor = (float) (Math.abs(0.5 - position) * 2);
//            tv.setAlpha(alphaFactor);
            if (alphaFactor < 0.05) {
                if (listener != null) {
                    listener.setText(nowPosition);
                }
            }
            if (alphaFactor > 0.8) {
                view.setScaleX(alphaFactor);
                view.setScaleY(alphaFactor);
            } else if (alphaFactor < 0.2) {
                view.setScaleX(1 - alphaFactor);
                view.setScaleY(1 - alphaFactor);
            } else {
                view.setScaleX(0.8f);
                view.setScaleY(0.8f);
            }
        }
        if (position < 0 && position > -1) {
            float alphaFactor = ((1 + position));
            if (alphaFactor > 0.8) {
                view.setScaleX(alphaFactor);
                view.setScaleY(alphaFactor);
            } else if (alphaFactor < 0.2) {
                view.setScaleX(1 - alphaFactor);
                view.setScaleY(1 - alphaFactor);
            } else {
                view.setScaleX(0.8f);
                view.setScaleY(0.8f);
            }
        }

        if (position < -1) {
            //出了上面屏幕
            view.setAlpha(0);
        } else if (position <= 1) {
            if (position < 0) {
                //消失的页面
//                view.setTranslationY(-pageHeight * position);//阻止消失的页面移动
            } else {
                //出现的页面
//                view.setTranslationY(pageHeight);//直接设置出现的页面到底部
//                view.setTranslationY(-pageHeight * position);//阻止出现的页面滑动
            }

            float alphaFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position * 2));

            view.setAlpha(alphaFactor);
        } else {
            //出了下边屏幕
            view.setAlpha(0);
        }
    }

    public interface ViewPagerTransnfornnmnerLnistneer {
        void setText(int nowPostion);
    }
}
