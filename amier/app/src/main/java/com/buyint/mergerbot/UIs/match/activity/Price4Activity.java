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
import com.buyint.mergerbot.dto.UnitValueModifierBean;
import com.buyint.mergerbot.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/3
 */

public class Price4Activity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llUsd, llRmb;
    private ImageView ivUsd;
    private ImageView ivRmb;
    private String huobi = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price4);
        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());

        String name = getIntent().getStringExtra(getString(R.string.NAME));
        TextView tvName = (TextView) findViewById(R.id.activity_price4_tv_name);
        tvName.setText(name);

        llUsd = (LinearLayout) findViewById(R.id.activity_price4_usd);
        llRmb = (LinearLayout) findViewById(R.id.activity_price4_rmb);
        ivUsd = (ImageView) findViewById(R.id.activity_price4_usd_iv);
        ivRmb = (ImageView) findViewById(R.id.activity_price4_rmb_iv);
        llUsd.setOnClickListener(this);
        llRmb.setOnClickListener(this);

        EditText etEbidtaMin = (EditText) findViewById(R.id.activity_price4_ebitda_min);
        EditText etEbidtaMax = (EditText) findViewById(R.id.activity_price4_ebitda_max);
        EditText etEarnMin = (EditText) findViewById(R.id.activity_price4_earn_min);
        EditText etEarnMax = (EditText) findViewById(R.id.activity_price4_earn_max);
        EditText etNetprofitMin = (EditText) findViewById(R.id.activity_price4_netprofit_min);
        EditText etNetprofitMax = (EditText) findViewById(R.id.activity_price4_netprofit_max);

        findViewById(R.id.activity_price4_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(huobi)) {
                    return;
                }
                if (TextUtils.isEmpty(etEbidtaMin.getText().toString()) && TextUtils.isEmpty(etEbidtaMax.getText().toString()) && TextUtils.isEmpty(etEarnMin.getText().toString()) && TextUtils.isEmpty(etNetprofitMax.getText().toString()) && TextUtils.isEmpty(etNetprofitMin.getText().toString()) && TextUtils.isEmpty(etEarnMax.getText().toString())) {
                    return;
                }
                double ebidtaMin = 0;
                double ebidtaMax = 0;
                double earnMin = 0;
                double earnMax = 0;
                double netProfitMin = 0;
                double netProfitMax = 0;
                if (!TextUtils.isEmpty(etEbidtaMin.getText().toString())) {
                    ebidtaMin = Double.parseDouble(etEbidtaMin.getText().toString());
                }
                if (!TextUtils.isEmpty(etEbidtaMax.getText().toString())) {
                    ebidtaMax = Double.parseDouble(etEbidtaMax.getText().toString());
                }
                if (!TextUtils.isEmpty(etEarnMin.getText().toString())) {
                    earnMin = Double.parseDouble(etEarnMin.getText().toString());
                }
                if (!TextUtils.isEmpty(etEarnMax.getText().toString())) {
                    earnMax = Double.parseDouble(etEarnMax.getText().toString());
                }
                if (!TextUtils.isEmpty(etNetprofitMin.getText().toString())) {
                    netProfitMin = Double.parseDouble(etNetprofitMin.getText().toString());
                }
                if (!TextUtils.isEmpty(etNetprofitMax.getText().toString())) {
                    netProfitMax = Double.parseDouble(etNetprofitMax.getText().toString());
                }

                //ebitda组装
                UnitValueBean uu1 = new UnitValueBean();
                UnitValueBean uu2 = new UnitValueBean();
                uu1.value = ebidtaMin;
                uu2.value = ebidtaMax;
                uu1.unit = huobi;
                uu2.unit = huobi;

                //营业额（营收）组装
                UnitValueModifierBean qq1 = new UnitValueModifierBean();
                UnitValueModifierBean qq2 = new UnitValueModifierBean();
                qq1.value = earnMin;
                qq2.value = earnMax;
                qq1.unit = huobi;
                qq2.unit = huobi;

                //净利润组装
                UnitValueBean ww1 = new UnitValueBean();
                UnitValueBean ww2 = new UnitValueBean();
                ww1.value = netProfitMin;
                ww2.value = netProfitMax;
                ww1.unit = huobi;
                ww2.unit = huobi;

                Intent intent = new Intent();
                intent.putExtra(getString(R.string.ebidta1), uu1);
                intent.putExtra(getString(R.string.ebidta2), uu2);
                intent.putExtra(getString(R.string.earn1), qq1);
                intent.putExtra(getString(R.string.earn2), qq2);
                intent.putExtra(getString(R.string.netprofit1), ww1);
                intent.putExtra(getString(R.string.netprofit2), ww2);

                setResult(1001, intent);
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        ArrayList<UnitValueBean> ebitda = (ArrayList<UnitValueBean>) intent.getSerializableExtra(getString(R.string.EBITDA));
        ArrayList<UnitValueModifierBean> turnover = (ArrayList<UnitValueModifierBean>) intent.getSerializableExtra(getString(R.string.TURNOVER));
        ArrayList<UnitValueBean> netProfit = (ArrayList<UnitValueBean>) intent.getSerializableExtra(getString(R.string.NET_PROFIT));

        if (ebitda != null && ebitda.size() > 0) {
            UnitValueBean u1 = ebitda.get(0);
            UnitValueBean u2 = ebitda.get(1);
            if ("$".equals(u1.unit)) {
                $Click();
            } else if ("￥".equals(u1.unit)) {
                ￥Click();
            }
            etEbidtaMin.setText("" + u1.value);
            etEbidtaMax.setText("" + u2.value);
        }
        if (turnover != null && turnover.size() > 0) {
            UnitValueModifierBean b1 = turnover.get(0);
            UnitValueModifierBean b2 = turnover.get(1);
            if ("$".equals(b1.unit)) {
                $Click();
            } else if ("￥".equals(b1.unit)) {
                ￥Click();
            }
            etEarnMin.setText("" + b1.value);
            etEarnMax.setText("" + b2.value);
        }
        if (netProfit != null && netProfit.size() > 0) {
            UnitValueBean u1 = netProfit.get(0);
            UnitValueBean u2 = netProfit.get(1);
            if ("$".equals(u1.unit)) {
                $Click();
            } else if ("￥".equals(u1.unit)) {
                ￥Click();
            }
            etNetprofitMin.setText("" + u1.value);
            etNetprofitMax.setText("" + u2.value);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_price4_usd:
                $Click();
                break;
            case R.id.activity_price4_rmb:
                ￥Click();
                break;
        }
    }

    private void ￥Click() {
        ivRmb.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
        ivUsd.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        huobi = "￥";
    }

    private void $Click() {
        ivUsd.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
        ivRmb.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        huobi = "$";
    }
}
