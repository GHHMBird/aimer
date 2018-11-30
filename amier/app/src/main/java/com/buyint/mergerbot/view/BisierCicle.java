package com.buyint.mergerbot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
 * Created by CXC on 2018/4/3.
 */

public class BisierCicle extends View {

    //第一个点
    private float mStartPointX1;
    private float mStartPointY1;

    //第二个点
    private float mStartPointX2;
    private float mStartPointY2;

    //第三个点
    private float mStartPointX3;
    private float mStartPointY3;

    //第四个点
    private float mStartPointX4;
    private float mStartPointY4;

    //第五个点
    private float mStartPointX5;
    private float mStartPointY5;

    //第六个点
    private float mStartPointX6;
    private float mStartPointY6;

    //第七个点
    private float mStartPointX7;
    private float mStartPointY7;

    //第八个点
    private float mStartPointX8;
    private float mStartPointY8;

    //第一个线
    private float mGoingPointX1;
    private float mGoingPointY1;
    private Path path1;

    //第二个线
    private float mGoingPointX2;
    private float mGoingPointY2;
    private Path path2;

    //第三个线
    private float mGoingPointX3;
    private float mGoingPointY3;
    private Path path3;

    //第四个线
    private float mGoingPointX4;
    private float mGoingPointY4;
    private Path path4;

    //第五个线
    private float mGoingPointX5;
    private float mGoingPointY5;
    private Path path5;

    //第六个线
    private float mGoingPointX6;
    private float mGoingPointY6;
    private Path path6;

    //第七个线
    private float mGoingPointX7;
    private float mGoingPointY7;
    private Path path7;

    //第八个线
    private float mGoingPointX8;
    private float mGoingPointY8;
    private Path path8;

    private Paint mPaintBezier;
    private Paint mPaintGoing;
    private Paint mPaintText;

    public BisierCicle(Context context) {
        super(context);
    }

    public BisierCicle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintGoing = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(3);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(20);
    }

    public BisierCicle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX1 = w / 3 * 2;
        mStartPointY1 = h / 5;

        mStartPointX2 = (float) (w * 0.82);
        mStartPointY2 = (float) (h * 0.42);

        mStartPointX3 = (float) (w * 0.775);
        mStartPointY3 = h / 3 * 2;

        mStartPointX4 = (float) (w * 0.575);
        mStartPointY4 = (float) (h * 0.82);

        mStartPointX5 = w / 3;
        mStartPointY5 = (float) (h * 0.775);

        mStartPointX6 = w / 6;
        mStartPointY6 = (float) (h * 0.575);

        mStartPointX7 = w / 5;
        mStartPointY7 = h / 3;

        mStartPointX8 = (float) (w * 0.42);
        mStartPointY8 = h / 6;

        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        path4 = new Path();
        path5 = new Path();
        path6 = new Path();
        path7 = new Path();
        path8 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //路径建立
        path1.reset();
        path1.moveTo(mStartPointX1, mStartPointY1);
        path1.quadTo(mGoingPointX1, mGoingPointY1, mStartPointX2, mStartPointY2);
        path2.reset();
        path2.moveTo(mStartPointX2, mStartPointY2);
        path2.quadTo(mGoingPointX2, mGoingPointY2, mStartPointX3, mStartPointY3);
        path3.reset();
        path3.moveTo(mStartPointX3, mStartPointY3);
        path3.quadTo(mGoingPointX3, mGoingPointY3, mStartPointX4, mStartPointY4);
        path4.reset();
        path4.moveTo(mStartPointX4, mStartPointY4);
        path4.quadTo(mGoingPointX4, mGoingPointY4, mStartPointX5, mStartPointY5);
        path5.reset();
        path5.moveTo(mStartPointX5, mStartPointY5);
        path5.quadTo(mGoingPointX5, mGoingPointY5, mStartPointX6, mStartPointY6);
        path6.reset();
        path6.moveTo(mStartPointX6, mStartPointY6);
        path6.quadTo(mGoingPointX6, mGoingPointY6, mStartPointX7, mStartPointY7);
        path7.reset();
        path7.moveTo(mStartPointX7, mStartPointY7);
        path7.quadTo(mGoingPointX7, mGoingPointY7, mStartPointX8, mStartPointY8);
        path8.reset();
        path8.moveTo(mStartPointX8, mStartPointY8);
        path8.quadTo(mGoingPointX8, mGoingPointY8, mStartPointX1, mStartPointY1);
        //画起点
        canvas.drawPoint(mStartPointX1, mStartPointY1, mPaintGoing);
        canvas.drawPoint(mStartPointX2, mStartPointY2, mPaintGoing);
        canvas.drawPoint(mStartPointX3, mStartPointY3, mPaintGoing);
        canvas.drawPoint(mStartPointX4, mStartPointY4, mPaintGoing);
        canvas.drawPoint(mStartPointX5, mStartPointY5, mPaintGoing);
        canvas.drawPoint(mStartPointX6, mStartPointY6, mPaintGoing);
        canvas.drawPoint(mStartPointX7, mStartPointY7, mPaintGoing);
        canvas.drawPoint(mStartPointX8, mStartPointY8, mPaintGoing);
        //画终点
        canvas.drawPoint(mStartPointX2, mStartPointY2, mPaintGoing);
        canvas.drawPoint(mStartPointX3, mStartPointY3, mPaintGoing);
        canvas.drawPoint(mStartPointX4, mStartPointY4, mPaintGoing);
        canvas.drawPoint(mStartPointX5, mStartPointY5, mPaintGoing);
        canvas.drawPoint(mStartPointX6, mStartPointY6, mPaintGoing);
        canvas.drawPoint(mStartPointX7, mStartPointY7, mPaintGoing);
        canvas.drawPoint(mStartPointX8, mStartPointY8, mPaintGoing);
        canvas.drawPoint(mStartPointX1, mStartPointY1, mPaintGoing);
        //画路线
        canvas.drawPath(path1, mPaintBezier);
        canvas.drawPath(path2, mPaintBezier);
        canvas.drawPath(path3, mPaintBezier);
        canvas.drawPath(path4, mPaintBezier);
        canvas.drawPath(path5, mPaintBezier);
        canvas.drawPath(path6, mPaintBezier);
        canvas.drawPath(path7, mPaintBezier);
        canvas.drawPath(path8, mPaintBezier);
    }

    public void start() {
        Message obtain = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putFloat("mStartPointX1", (mStartPointX1 + mStartPointX2) / 2);
        bundle.putFloat("mStartPointY1", (mStartPointY1 + mStartPointY2) / 2);
        bundle.putFloat("mStartPointX2", (mStartPointX2 + mStartPointX3) / 2);
        bundle.putFloat("mStartPointY2", (mStartPointY2 + mStartPointY3) / 2);
        bundle.putFloat("mStartPointX3", (mStartPointX3 + mStartPointX4) / 2);
        bundle.putFloat("mStartPointY3", (mStartPointY3 + mStartPointY4) / 2);
        bundle.putFloat("mStartPointX4", (mStartPointX4 + mStartPointX5) / 2);
        bundle.putFloat("mStartPointY4", (mStartPointY4 + mStartPointY5) / 2);
        bundle.putFloat("mStartPointX5", (mStartPointX5 + mStartPointX6) / 2);
        bundle.putFloat("mStartPointY5", (mStartPointY5 + mStartPointY6) / 2);
        bundle.putFloat("mStartPointX6", (mStartPointX6 + mStartPointX7) / 2);
        bundle.putFloat("mStartPointY6", (mStartPointY6 + mStartPointY7) / 2);
        bundle.putFloat("mStartPointX7", (mStartPointX7 + mStartPointX8) / 2);
        bundle.putFloat("mStartPointY7", (mStartPointY7 + mStartPointY8) / 2);
        bundle.putFloat("mStartPointX8", (mStartPointX8 + mStartPointX1) / 2);
        bundle.putFloat("mStartPointY8", (mStartPointY8 + mStartPointY1) / 2);
        obtain.setData(bundle);
        handler.sendMessage(obtain);
    }

    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            mGoingPointX1 = data.getFloat("mStartPointX1");
            mGoingPointY1 = data.getFloat("mStartPointY1");
            mGoingPointX2 = data.getFloat("mStartPointX2");
            mGoingPointY2 = data.getFloat("mStartPointY2");
            mGoingPointX3 = data.getFloat("mStartPointX3");
            mGoingPointY3 = data.getFloat("mStartPointY3");
            mGoingPointX4 = data.getFloat("mStartPointX4");
            mGoingPointY4 = data.getFloat("mStartPointY4");
            mGoingPointX5 = data.getFloat("mStartPointX5");
            mGoingPointY5 = data.getFloat("mStartPointY5");
            mGoingPointX6 = data.getFloat("mStartPointX6");
            mGoingPointY6 = data.getFloat("mStartPointY6");
            mGoingPointX7 = data.getFloat("mStartPointX7");
            mGoingPointY7 = data.getFloat("mStartPointY7");
            mGoingPointX8 = data.getFloat("mStartPointX8");
            mGoingPointY8 = data.getFloat("mStartPointY8");
            invalidate();
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putFloat("mStartPointX1", random(mStartPointX1, mStartPointX2));
            bundle.putFloat("mStartPointY1", random(mStartPointY1, mStartPointY2));

            bundle.putFloat("mStartPointX2", random(mStartPointX3, mStartPointX2));
            bundle.putFloat("mStartPointY2", random(mStartPointY2, mStartPointY3));

            bundle.putFloat("mStartPointX3", random(mStartPointX4, mStartPointX3));
            bundle.putFloat("mStartPointY3", random(mStartPointY3, mStartPointY4));

            bundle.putFloat("mStartPointX4", random(mStartPointX5, mStartPointX4));
            bundle.putFloat("mStartPointY4", random(mStartPointY5, mStartPointY4));

            bundle.putFloat("mStartPointX5", random(mStartPointX6, mStartPointX5));
            bundle.putFloat("mStartPointY5", random(mStartPointY6, mStartPointY5));

            bundle.putFloat("mStartPointX6", random(mStartPointX6, mStartPointX7));
            bundle.putFloat("mStartPointY6", random(mStartPointY7, mStartPointY6));

            bundle.putFloat("mStartPointX7", random(mStartPointX7, mStartPointX8));
            bundle.putFloat("mStartPointY7", random(mStartPointY8, mStartPointY7));

            bundle.putFloat("mStartPointX8", random(mStartPointX8, mStartPointX1));
            bundle.putFloat("mStartPointY8", random(mStartPointY8, mStartPointY1));
            obtain.setData(bundle);
            handler.sendMessageDelayed(obtain, 100);
        }
    };

    public float random(float d1, float d2) {
        float v = (float) (d1 + Math.random() * d2);
        if (v < d2 && v > d1) {
            return v;
        } else {
            return (d1 + d2) / 2;
        }
    }
}
