package com.buyint.mergerbot.UIs.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.UnitValueBean;
import com.buyint.mergerbot.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/3
 */

public class PriceActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llUsd, llRmb;
    private ImageView ivUsd;
    private ImageView ivRmb;
    private String huobi = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        findViewById(R.id.toolbar_image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String name = getIntent().getStringExtra(getString(R.string.NAME));
        TextView tvName = (TextView) findViewById(R.id.activity_price_tv_name);
        tvName.setText(name);

        llUsd = (LinearLayout) findViewById(R.id.activity_price_usd);
        llRmb = (LinearLayout) findViewById(R.id.activity_price_rmb);
        ivUsd = (ImageView) findViewById(R.id.activity_price_usd_iv);
        ivRmb = (ImageView) findViewById(R.id.activity_price_rmb_iv);
        llUsd.setOnClickListener(this);
        llRmb.setOnClickListener(this);

        EditText etMin = (EditText) findViewById(R.id.activity_price_min);
        EditText etMax = (EditText) findViewById(R.id.activity_price_max);

        findViewById(R.id.activity_price_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(huobi)) {
                    return;
                }
                if (TextUtils.isEmpty(etMin.getText().toString()) && TextUtils.isEmpty(etMax.getText().toString())) {
                    return;
                }
                double min = 0;
                double max = 0;
                if (!TextUtils.isEmpty(etMin.getText().toString())) {
                    min = Double.parseDouble(etMin.getText().toString());
                }
                if (!TextUtils.isEmpty(etMax.getText().toString())) {
                    max = Double.parseDouble(etMax.getText().toString());
                }
                UnitValueBean u1 = new UnitValueBean();
                UnitValueBean u2 = new UnitValueBean();
                u1.unit = huobi;
                u2.unit = huobi;
                u1.value = min;
                u2.value = max;

                Intent intent = new Intent();
                intent.putExtra(getString(R.string.data1), u1);
                intent.putExtra(getString(R.string.data2), u2);
                setResult(1001, intent);
                onBackPressed();
            }
        });

        ArrayList<UnitValueBean> list = (ArrayList<UnitValueBean>) getIntent().getSerializableExtra(getString(R.string.LIST));
        if (list.size() > 0) {
            UnitValueBean u1 = list.get(0);
            UnitValueBean u2 = list.get(1);
            huobi = u1.unit;
            if("$".equals(huobi)){
                ivUsd.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
            }else if("￥".equals(huobi)){
                ivRmb.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
            }
            if (u1.value != 0) {
                etMin.setText("" + u1.value);
            }
            if (u2.value != 0) {
                etMax.setText("" + u2.value);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_price_usd:
                ivUsd.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                ivRmb.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                huobi = "$";
                break;
            case R.id.activity_price_rmb:
                ivRmb.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                ivUsd.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                huobi = "￥";
                break;
        }
    }
}
