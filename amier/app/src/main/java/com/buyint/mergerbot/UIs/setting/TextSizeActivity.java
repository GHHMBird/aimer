package com.buyint.mergerbot.UIs.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.SeekBar;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.bus.Bus;
import com.buyint.mergerbot.stroage.PreferencesKeeper;

import static com.buyint.mergerbot.base.AppApplication.context;

/**
 * Created by CXC on 2018/4/27
 */

public class TextSizeActivity extends BaseActivity {

    private SeekBar se;
    private TextView tv;
    private TextView tvTitle, tv1, tv2, tv3, tv4, tv5, tv6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size);

        TextView title = findViewById(R.id.toorbar_title);
        title.setText(getString(R.string.text_size));

        tvTitle = findViewById(R.id.toorbar_title);
        tv1 = findViewById(R.id.item_result2_title);
        tv2 = findViewById(R.id.item_result2_place);
        tv3 = findViewById(R.id.item_result2_industry);
        tv4 = findViewById(R.id.item_result2_price);
        tv5 = findViewById(R.id.item_result2_come);
        tv6 = findViewById(R.id.item_result2_type);

        tv1.setText(getString(R.string.test_text_string));
        tv3.setText(getString(R.string.internet));
        tv4.setText(getString(R.string.money_100000000000));
        tv5.setText(getString(R.string.acquisition));
        tv6.setText(getString(R.string.test_company_string));

        if (BaseActivity.fontScale == 0.8f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 0.9f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.0f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.1f) {
            tv1.setMaxLines(1);
        } else if (BaseActivity.fontScale == 1.2f) {
            tv1.setMaxLines(1);
        }

        se = findViewById(R.id.activity_text_size_seek);
        tv = findViewById(R.id.activity_text_size_tv);
        se.setMax(100);
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());

        int textSize = PreferencesKeeper.getInt(this, getString(R.string.TEXTSIZE), 3);

        if (1 == textSize) {
            se.setProgress(10);
            se.setThumb(ContextCompat.getDrawable(this, R.drawable.progress_959dce));
            tv.setText(getString(R.string.very_small));
        } else if (2 == textSize) {
            se.setProgress(30);
            se.setThumb(ContextCompat.getDrawable(this, R.drawable.progress_959dce));
            tv.setText(getString(R.string.small));
        } else if (3 == textSize) {
            se.setProgress(50);
            se.setThumb(ContextCompat.getDrawable(this, R.drawable.progress_f793le));
            tv.setText(getString(R.string.standard));
        } else if (4 == textSize) {
            se.setProgress(70);
            se.setThumb(ContextCompat.getDrawable(this, R.drawable.progress_bf3e75));
            tv.setText(getString(R.string.big));
        } else if (5 == textSize) {
            se.setProgress(90);
            se.setThumb(ContextCompat.getDrawable(this, R.drawable.progress_bf3e75));
            tv.setText(getString(R.string.very_big));
        }

        se.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 20) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_959dce));
                    tv.setText(getString(R.string.very_small));
                    BaseActivity.fontScale = 0.8f;
                    seekBar.invalidate();
                } else if (progress >= 20 && progress < 40) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_959dce));
                    tv.setText(getString(R.string.small));
                    BaseActivity.fontScale = 0.9f;
                    seekBar.invalidate();
                } else if (progress >= 40 && progress < 60) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_two));
                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_f7931e));
                    tv.setText(getString(R.string.standard));
                    BaseActivity.fontScale = 1.0f;
                    seekBar.invalidate();
                } else if (progress >= 60 && progress < 80) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_three));
                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_bf3e75));
                    tv.setText(getString(R.string.big));
                    BaseActivity.fontScale = 1.1f;
                    seekBar.invalidate();
                } else if (progress >= 80) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_three));
                    tv.setTextColor(ContextCompat.getColor(context, R.color.color_bf3e75));
                    tv.setText(getString(R.string.very_big));
                    BaseActivity.fontScale = 1.2f;
                    seekBar.invalidate();
                }
                setSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (progress < 20) {
                    seekBar.setProgress(10);
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                } else if (progress >= 20 && progress < 40) {
                    seekBar.setProgress(30);
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                } else if (progress >= 40 && progress < 60) {
                    seekBar.setProgress(50);
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_two));
                } else if (progress >= 60 && progress < 80) {
                    seekBar.setProgress(70);
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_three));
                } else if (progress >= 80) {
                    seekBar.setProgress(90);
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_three));
                }
            }
        });
        findViewById(R.id.activity_text_size_add).setOnClickListener(v -> {
            if (se.getProgress() == 10) {
                se.setProgress(30);
            } else if (se.getProgress() == 30) {
                se.setProgress(50);
            } else if (se.getProgress() == 50) {
                se.setProgress(70);
            } else if (se.getProgress() == 70) {
                se.setProgress(90);
            }
        });
        findViewById(R.id.activity_text_size_jian).setOnClickListener(v -> {
            if (se.getProgress() == 90) {
                se.setProgress(70);
            } else if (se.getProgress() == 70) {
                se.setProgress(50);
            } else if (se.getProgress() == 50) {
                se.setProgress(30);
            } else if (se.getProgress() == 30) {
                se.setProgress(10);
            }
        });
    }

    @Override
    public void onBackPressed() {
        int progress = se.getProgress();
        if (progress == 10) {
            PreferencesKeeper.putInt(this, getString(R.string.TEXTSIZE), 1);
            fontScale = 0.8f;
        } else if (progress == 30) {
            PreferencesKeeper.putInt(this, getString(R.string.TEXTSIZE), 2);
            fontScale = 0.9f;
        } else if (progress == 50) {
            PreferencesKeeper.putInt(this, getString(R.string.TEXTSIZE), 3);
            fontScale = 1.0f;
        } else if (progress == 70) {
            PreferencesKeeper.putInt(this, getString(R.string.TEXTSIZE), 4);
            fontScale = 1.1f;
        } else if (progress == 90) {
            PreferencesKeeper.putInt(this, getString(R.string.TEXTSIZE), 5);
            fontScale = 1.2f;
        }
        Bus.getDefault().post(getString(R.string.buyint_text_size_change));
        super.onBackPressed();
    }

    private void setSize() {
        //改变当前字体大小
        if (fontScale == 0.8f) {
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9.6f);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9.6f);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9.6f);
            tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9.6f);
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9.6f);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.2f);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.2f);
        } else if (fontScale == 0.9f) {
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.8f);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.8f);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.8f);
            tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.8f);
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.8f);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.1f);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.1f);
        } else if (fontScale == 1.0f) {
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        } else if (fontScale == 1.1f) {
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.2f);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.2f);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.2f);
            tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.2f);
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.2f);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.9f);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.9f);
        } else if (fontScale == 1.2f) {
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.4f);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.4f);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.4f);
            tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.4f);
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.4f);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.8f);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.8f);
        }

        if (BaseActivity.fontScale == 0.8f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 0.9f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.0f) {
            tv1.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.1f) {
            tv1.setMaxLines(1);
        } else if (BaseActivity.fontScale == 1.2f) {
            tv1.setMaxLines(1);
        }
    }
}
