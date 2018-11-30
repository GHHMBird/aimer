package com.buyint.mergerbot.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.Utility.VibratorUtil;

/**
 * Created by CXC on 2018/5/10
 */

public class AimerLogoImageView extends ImageView implements View.OnClickListener {
    public AimerLogoImageView(Context context) {
        super(context);
        initClick();
    }

    public AimerLogoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initClick();
    }

    public AimerLogoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClick();
    }

    public void initClick() {
        setOnClickListener(this);
//        setImageResource(R.drawable.aimer_default);
        AnimationDrawable ad = (AnimationDrawable) getDrawable();
        ad.start();
    }

    public void iconStartAndStop() {
        setImageResource(R.drawable.speak_icon_anim);
        AnimationDrawable ad = (AnimationDrawable) getDrawable();
        if (ad != null) {
            ad.start();
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AnimationDrawable ad = (AnimationDrawable) getDrawable();
            if (ad != null) {
                ad.stop();
                setImageResource(R.drawable.speak_icon_anim);
                AnimationDrawable ad2 = (AnimationDrawable) getDrawable();
                ad2.start();
            }
        }
    };

    @Override
    public void onClick(View v) {
        VibratorUtil.vibrate(v.getContext(), 100);
        iconStartAndStop();
    }
}
