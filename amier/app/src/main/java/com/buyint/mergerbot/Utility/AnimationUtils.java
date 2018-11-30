package com.buyint.mergerbot.Utility;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.buyint.mergerbot.R;

import java.util.Random;

public class AnimationUtils {

    public interface AnimationListener {
        void onFinish();
    }

    public static void slideToUp(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public static void slideToDown(View view, final AnimationListener listener) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static void alpha01Animate(View view, int millSconds, final AnimationListener listener) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.activity_rotate_0_1);
        view.setAnimation(animation);
        animation.setDuration(millSconds);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animation.start();
    }

    public static void alpha10Animate(View view, int millSconds, final AnimationListener listener) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.activity_rotate_1_0);
        view.setAnimation(animation);
        animation.setDuration(millSconds);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animation.start();
    }

    public static void recyclerViewItemAnimation(Context context, RecyclerView vp) {
        int random = new Random().nextInt(3);
        LayoutAnimationController controller = null;
        if (random == 0) {
            controller = android.view.animation.AnimationUtils.loadLayoutAnimation(context, R.anim.viewgroup_layout_anim1);
        } else if (random == 1) {
            controller = android.view.animation.AnimationUtils.loadLayoutAnimation(context, R.anim.viewgroup_layout_anim2);
        } else if (random == 2) {
            controller = android.view.animation.AnimationUtils.loadLayoutAnimation(context, R.anim.viewgroup_layout_anim3);
        }
        vp.setLayoutAnimation(controller);

        vp.getAdapter().notifyDataSetChanged();
        vp.scheduleLayoutAnimation();
    }

    public static void qaRecyclerviewItemAnimation(Context context, RecyclerView recyclerView) {
        LayoutAnimationController controller = android.view.animation.AnimationUtils.loadLayoutAnimation(context, R.anim.viewgroup_layout_anim3);
        recyclerView.setLayoutAnimation(controller);

        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    public static RotateAnimation initRotateAnimation(long duration, int fromAngle, int toAngle, boolean isFillAfter, int repeatCount) {
        RotateAnimation mLoadingRotateAnimation = new RotateAnimation(fromAngle, toAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lirInterpolator = new LinearInterpolator();
        mLoadingRotateAnimation.setInterpolator(lirInterpolator);
        mLoadingRotateAnimation.setDuration(duration);
        mLoadingRotateAnimation.setFillAfter(isFillAfter);
        mLoadingRotateAnimation.setRepeatCount(repeatCount);
        mLoadingRotateAnimation.setRepeatMode(Animation.RESTART);
        return mLoadingRotateAnimation;
    }

    public static RotateAnimation initRotateAnimation(boolean isClockWise, long duration, boolean isFillAfter, int repeatCount) {
        int endAngle;
        if (isClockWise) {
            endAngle = 360;
        } else {
            endAngle = -360;
        }
        RotateAnimation mLoadingRotateAnimation = new RotateAnimation(0, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lirInterpolator = new LinearInterpolator();
        mLoadingRotateAnimation.setInterpolator(lirInterpolator);
        mLoadingRotateAnimation.setDuration(duration);
        mLoadingRotateAnimation.setFillAfter(isFillAfter);
        mLoadingRotateAnimation.setRepeatCount(repeatCount);
        mLoadingRotateAnimation.setRepeatMode(Animation.RESTART);
        return mLoadingRotateAnimation;
    }

    public static AnimationDrawable initAnimationDrawable(Context context, int[] drawableIds, int durationTime, boolean isOneShot) {
        AnimationDrawable mAnimationDrawable = new AnimationDrawable();
        for (int i = 0; i < drawableIds.length; i++) {
            int id = drawableIds[i];
            mAnimationDrawable.addFrame(context.getResources().getDrawable(id), durationTime);
        }
        mAnimationDrawable.setOneShot(isOneShot);
        return mAnimationDrawable;
    }

    public static Animation initAlphaAnimtion(Context context, float fromAlpha, float toAlpha, long duration) {
        Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        return alphaAnimation;
    }
}
