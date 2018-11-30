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
 * Created by CXC on 2018/5/4
 */

public class FinancePrice4Activity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llUsd, llRmb, llEarn, llUnearn;
    private ImageView ivRmb, ivUsd, ivEarn, ivUnearn;
    private String huobi = "";
    private LinearLayout llShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_price4);
        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());

        String name = getIntent().getStringExtra(getString(R.string.NAME));
        TextView tvName = (TextView) findViewById(R.id.activity_finance_price4_tv_name);
        tvName.setText(name);

        llUsd = (LinearLayout) findViewById(R.id.activity_finance_price4_usd);
        llRmb = (LinearLayout) findViewById(R.id.activity_finance_price4_rmb);
        ivUsd = (ImageView) findViewById(R.id.activity_finance_price4_usd_iv);
        ivRmb = (ImageView) findViewById(R.id.activity_finance_price4_rmb_iv);
        llEarn = (LinearLayout) findViewById(R.id.activity_finance_price4_earn_ll);
        llUnearn = (LinearLayout) findViewById(R.id.activity_finance_price4_unearn_ll);
        ivEarn = (ImageView) findViewById(R.id.activity_finance_price4_earn_iv);
        ivUnearn = (ImageView) findViewById(R.id.activity_finance_price4_unearn_iv);
        llUsd.setOnClickListener(this);
        llRmb.setOnClickListener(this);
        llEarn.setOnClickListener(this);
        llUnearn.setOnClickListener(this);

        llShow = (LinearLayout) findViewById(R.id.activity_finance_price4_show);

        EditText etEbidtaMax = (EditText) findViewById(R.id.activity_finance_price4_ebitda_max);
        EditText etEarnMax = (EditText) findViewById(R.id.activity_finance_price4_earn_max);
        EditText etNetprofitMax = (EditText) findViewById(R.id.activity_finance_price4_netprofit_max);
        EditText etCashFlowMax = (EditText) findViewById(R.id.activity_finance_price4_cashflow_max);

        findViewById(R.id.activity_finance_price4_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llShow.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(huobi)) {
                        return;
                    }
                    if (TextUtils.isEmpty(etCashFlowMax.getText().toString()) && TextUtils.isEmpty(etEbidtaMax.getText().toString()) && TextUtils.isEmpty(etNetprofitMax.getText().toString()) && TextUtils.isEmpty(etEarnMax.getText().toString())) {
                        return;
                    }
                    double ebidtaMax = 0;
                    double earnMax = 0;
                    double netProfitMax = 0;
                    double cashflowMax = 0;
                    if (!TextUtils.isEmpty(etEbidtaMax.getText().toString())) {
                        ebidtaMax = Double.parseDouble(etEbidtaMax.getText().toString());
                    }
                    if (!TextUtils.isEmpty(etEarnMax.getText().toString())) {
                        earnMax = Double.parseDouble(etEarnMax.getText().toString());
                    }
                    if (!TextUtils.isEmpty(etNetprofitMax.getText().toString())) {
                        netProfitMax = Double.parseDouble(etNetprofitMax.getText().toString());
                    }
                    if (!TextUtils.isEmpty(etCashFlowMax.getText().toString())) {
                        cashflowMax = Double.parseDouble(etCashFlowMax.getText().toString());
                    }

                    //ebitda组装
                    UnitValueBean uu2 = new UnitValueBean();
                    uu2.value = ebidtaMax;
                    uu2.unit = huobi;

                    //营业额（营收）组装
                    UnitValueModifierBean qq2 = new UnitValueModifierBean();
                    qq2.value = earnMax;
                    qq2.unit = huobi;

                    //净利润组装
                    UnitValueBean ww2 = new UnitValueBean();
                    ww2.value = netProfitMax;
                    ww2.unit = huobi;

                    //现金流组装
                    UnitValueBean c2 = new UnitValueBean();
                    c2.unit = huobi;
                    c2.value = cashflowMax;

                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.ISGAIN), true);
                    intent.putExtra(getString(R.string.ebidta2), uu2);
                    intent.putExtra(getString(R.string.earn2), qq2);
                    intent.putExtra(getString(R.string.netprofit2), ww2);
                    intent.putExtra(getString(R.string.cashflow2), c2);

                    setResult(1001, intent);
                    onBackPressed();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.ISGAIN), false);
                    setResult(1001, intent);
                    onBackPressed();
                }
            }
        });

        Intent intent = getIntent();
        boolean isGain = intent.getBooleanExtra(getString(R.string.ISGAIN), false);
        if (isGain) {
            ArrayList<UnitValueBean> ebitda = (ArrayList<UnitValueBean>) intent.getSerializableExtra(getString(R.string.EBITDA));
            ArrayList<UnitValueModifierBean> turnover = (ArrayList<UnitValueModifierBean>) intent.getSerializableExtra(getString(R.string.TURNOVER));
            ArrayList<UnitValueBean> netProfit = (ArrayList<UnitValueBean>) intent.getSerializableExtra(getString(R.string.NET_PROFIT));
            ArrayList<UnitValueBean> cashFlow = (ArrayList<UnitValueBean>) intent.getSerializableExtra(getString(R.string.CASH_FLOW));
            if (ebitda != null && ebitda.size() > 0) {
                UnitValueBean u2 = ebitda.get(1);
                if ("$".equals(u2.unit)) {
                    $Click();
                } else if ("￥".equals(u2.unit)) {
                    ￥Click();
                }
                etEbidtaMax.setText("" + u2.value);
            }
            if (turnover != null && turnover.size() > 0) {
                UnitValueModifierBean b2 = turnover.get(1);
                if ("$".equals(b2.unit)) {
                    $Click();
                } else if ("￥".equals(b2.unit)) {
                    ￥Click();
                }
                etEarnMax.setText("" + b2.value);
            }
            if (netProfit != null && netProfit.size() > 0) {
                UnitValueBean u2 = netProfit.get(1);
                if ("$".equals(u2.unit)) {
                    $Click();
                } else if ("￥".equals(u2.unit)) {
                    ￥Click();
                }
                etNetprofitMax.setText("" + u2.value);
            }
            if (cashFlow != null && cashFlow.size() > 0) {
                UnitValueBean u2 = cashFlow.get(1);
                if ("$".equals(u2.unit)) {
                    $Click();
                } else if ("￥".equals(u2.unit)) {
                    ￥Click();
                }
                etCashFlowMax.setText("" + u2.value);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_finance_price4_usd:
                $Click();
                break;
            case R.id.activity_finance_price4_rmb:
                ￥Click();
                break;
            case R.id.activity_finance_price4_earn_ll:
                ivEarn.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                ivUnearn.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                llShow.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_finance_price4_unearn_ll:
                ivUnearn.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                ivEarn.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                llShow.setVisibility(View.GONE);
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
