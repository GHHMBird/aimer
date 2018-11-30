package com.buyint.mergerbot.UIs.match.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.Utility.AnimationUtils;
import com.buyint.mergerbot.dto.MoneyFragmentDto;

import java.util.ArrayList;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

@SuppressLint("ValidFragment")
public class QAMoneyFragment extends DialogFragment implements View.OnClickListener {

    private int type;//0 qa进入  1 add进入
    private QAMoneyFragmentListener listener;
    private Context context;
    private View viewRmb, viewUsd, mRootView, viewWan, viewYi, ivJian, ivJia, view1, view2;
    private TextView tvSure, tvStep;
    private EditText et;
    private SeekBar seekbar;
    private int choose = 0;//0交易金额 1息税前利润
    private ArrayList<MoneyFragmentDto> list;

    public QAMoneyFragment() {
    }

    public QAMoneyFragment(Context context, QAMoneyFragmentListener listener, int type) {
        this.context = context;
        this.listener = listener;
        this.type = type;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL;
        params.softInputMode = SOFT_INPUT_ADJUST_RESIZE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        list = new ArrayList<>();
        list.add(new MoneyFragmentDto());
        list.add(new MoneyFragmentDto());
        mRootView = inflater.inflate(R.layout.fragment_money_bottom, container, false);
        AnimationUtils.slideToUp(mRootView);
        tvStep = (TextView) mRootView.findViewById(R.id.fragment_money_bottom_step);
        tvSure = (TextView) mRootView.findViewById(R.id.fragment_money_bottom_sure);
        view1 = mRootView.findViewById(R.id.fragment_money_bottom_tv1);
        view2 = mRootView.findViewById(R.id.fragment_money_bottom_tv2);
        viewRmb = mRootView.findViewById(R.id.fragment_money_bottom_rmb);
        viewUsd = mRootView.findViewById(R.id.fragment_money_bottom_usd);
        viewWan = mRootView.findViewById(R.id.fragment_money_bottom_wan);
        viewYi = mRootView.findViewById(R.id.fragment_money_bottom_yi);
        viewRmb.setOnClickListener(this);
        viewUsd.setOnClickListener(this);
        viewWan.setOnClickListener(this);
        viewYi.setOnClickListener(this);
        tvStep.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);

        et = (EditText) mRootView.findViewById(R.id.item_progress_view_et);
        ivJian = mRootView.findViewById(R.id.item_progress_view_delete);
        ivJia = mRootView.findViewById(R.id.item_progress_view_add);
        seekbar = (SeekBar) mRootView.findViewById(R.id.item_progress_view_seekbar);

        seekbar.setMax(10000);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                    seekBar.invalidate();
                    et.setTextColor(ContextCompat.getColor(context, R.color.color_959dce));
                    et.setText(getString(R.string.unlimited));
                } else {
                    if (progress < 4000) {
                        seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_one));
                        et.setTextColor(ContextCompat.getColor(context, R.color.color_959dce));
                        seekBar.invalidate();
                    } else if (progress >= 4000 && progress < 6000) {
                        seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_two));
                        et.setTextColor(ContextCompat.getColor(context, R.color.color_f7931e));
                        seekBar.invalidate();
                    } else if (progress >= 6000) {
                        seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.select_three));
                        et.setTextColor(ContextCompat.getColor(context, R.color.color_bf3e75));
                        seekBar.invalidate();
                    }
                    et.setText(progress + "");
                }
                list.get(choose).price = et.getText().toString().trim();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (progress == 0) {
                    return;
                }
                if ("wan".equals(list.get(choose).beishu)) {
                    int i = (progress + 50) / 100;
                    if (i == 0) {
                        et.setText(getString(R.string.unlimited));
                    } else {
                        et.setText(i * 100 + "");
                    }
                }
                list.get(choose).price = et.getText().toString().trim();
            }
        });

        seekbar.setProgress(0);

        if (type == 0) {

        } else if (type == 1) {
            mRootView.findViewById(R.id.fragment_money_bottom_horizon_ll).setVisibility(View.GONE);
        }

        return mRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_money_bottom_sure:
                if (listener != null) {
                    listener.priceOk(list);
                }
                dismiss();
                break;
            case R.id.fragment_money_bottom_step:
                if (listener != null) {
                    listener.priceCannel();
                }
                dismiss();
                break;
            case R.id.fragment_money_bottom_rmb:
                viewRmb.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                viewUsd.setBackgroundColor(Color.WHITE);
                list.get(choose).danwei = "￥";
                break;
            case R.id.fragment_money_bottom_usd:
                viewUsd.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                viewRmb.setBackgroundColor(Color.WHITE);
                list.get(choose).danwei = "$";
                break;
            case R.id.fragment_money_bottom_wan:
                viewWan.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                viewYi.setBackgroundColor(Color.WHITE);
                list.get(choose).beishu = "wan";
                break;
            case R.id.fragment_money_bottom_yi:
                viewYi.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                viewWan.setBackgroundColor(Color.WHITE);
                list.get(choose).beishu = "yi";
                break;
            case R.id.fragment_money_bottom_tv1:
                if (choose == 1) {
                    list.get(choose).price = et.getText().toString().trim();
                    view1.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                    view2.setBackgroundColor(Color.WHITE);
                    choose = 0;
                    initData();
                }
                break;
            case R.id.fragment_money_bottom_tv2:
                if (choose == 0) {
                    list.get(choose).price = et.getText().toString().trim();
                    view2.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
                    view1.setBackgroundColor(Color.WHITE);
                    choose = 1;
                    initData();
                }
                break;
        }
    }

    public void initData() {
        MoneyFragmentDto dto = list.get(choose);
        et.setText(dto.price);
        if ("wan".equals(dto.beishu)) {
            viewWan.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
            viewYi.setBackgroundColor(Color.WHITE);
        } else {
            viewYi.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
            viewWan.setBackgroundColor(Color.WHITE);
        }
        if ("￥".equals(dto.danwei)) {
            viewRmb.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
            viewUsd.setBackgroundColor(Color.WHITE);
        } else {
            viewUsd.setBackground(ContextCompat.getDrawable(context, R.drawable.tv_bg_gray));
            viewRmb.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void dismiss() {
        AnimationUtils.slideToDown(mRootView, () -> QAMoneyFragment.super.dismiss());
    }

    public interface QAMoneyFragmentListener {
        void priceOk(ArrayList<MoneyFragmentDto> list);

        void priceCannel();
    }
}
