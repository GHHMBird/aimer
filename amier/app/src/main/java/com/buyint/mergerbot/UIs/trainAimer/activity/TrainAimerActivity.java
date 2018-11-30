package com.buyint.mergerbot.UIs.trainAimer.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.activity.QAHistoryActivity;
import com.buyint.mergerbot.base.BaseActivity;

/**
 * Created by huheming on 2018/9/14
 */

public class TrainAimerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_aimer);

        TextView tvRight = findViewById(R.id.toolbar_right);
        Drawable d = ContextCompat.getDrawable(this, R.mipmap.user_back_question);
        d.setBounds(0, 0, dp2px(24f), dp2px(24f));
        tvRight.setCompoundDrawables(null, null, d, null);
        findViewById(R.id.toolbar_right_rl).setOnClickListener(v -> startActivity(new Intent(this, TrainAimerHelpActivity.class)));
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.activity_train_aimer_rl1).setOnClickListener(v -> startActivity(new Intent(this, QAHistoryActivity.class)));
        findViewById(R.id.activity_train_aimer_rl2).setOnClickListener(v -> showToast(getString(R.string.by_comon_no_action)));
        findViewById(R.id.activity_train_aimer_rl3).setOnClickListener(v -> showToast(getString(R.string.by_comon_no_action)));


    }
}
