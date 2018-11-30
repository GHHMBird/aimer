package com.buyint.mergerbot.UIs.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.base.BaseActivity;

/**
 * Created by huheming on 2018/5/28
 */

public class TextActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView tvTitle =  findViewById(R.id.toorbar_title);
        RelativeLayout ivBack =  findViewById(R.id.toolbar_back);
        TextView tvText =  findViewById(R.id.actvity_text_text);
        ivBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String title = intent.getStringExtra(getString(R.string.TITLE));
        tvTitle.setText(title);
        int type = intent.getIntExtra(getString(R.string.TYPE), -1);
        switch (type) {
            case 0:
                tvText.setText(getString(R.string.aimer_agreement_text));
                break;
            case 1:
                tvText.setText(getString(R.string.privacy_policy_text));
                break;
        }
    }
}
