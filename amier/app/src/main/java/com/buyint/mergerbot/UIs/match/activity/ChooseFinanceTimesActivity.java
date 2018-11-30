package com.buyint.mergerbot.UIs.match.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/26
 */

public class ChooseFinanceTimesActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private CheckBox cb1, cb2, cb3;
    private boolean[] checker1 = new boolean[]{false, false, false, false, false, false}, checker2 = new boolean[]{false, false, false, false, false}, checker3 = new boolean[]{false, false, false, false, false, false};
    private TextView tv11, tv12, tv13, tv14, tv15, tv16, tv21, tv22, tv23, tv24, tv25, tv31, tv32, tv33, tv34, tv35, tv36;
    private TextView saveView;
    private boolean initData = true;
    private LinearLayout ll1, ll2, ll3, ll4, ll5;
    private View view1, view2, view3, view4, view5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_finance_times);

        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());
        saveView = (TextView) findViewById(R.id.activity_choose_finance_time_save);
        saveView.setOnClickListener(v -> {
            ArrayList<String> list = new ArrayList<>();
            if (cb1.isChecked() && checker1 != null) {
                if (checker1[0]) {
                    list.add("0101");
                }
                if (checker1[1]) {
                    list.add("0102");
                }
                if (checker1[2]) {
                    list.add("0103");
                }
                if (checker1[3]) {
                    list.add("0104");
                }
                if (checker1[4]) {
                    list.add("0105");
                }
                if (checker1[5]) {
                    list.add("0106");
                }
            }
            if (cb2.isChecked() && checker2 != null) {
                if (checker2[0]) {
                    list.add("0201");
                }
                if (checker2[1]) {
                    list.add("0202");
                }
                if (checker2[2]) {
                    list.add("0203");
                }
                if (checker2[3]) {
                    list.add("0204");
                }
                if (checker2[4]) {
                    list.add("0205");
                }
            }
            if (cb3.isChecked() && checker3 != null) {
                if (checker3[0]) {
                    list.add("0301");
                }
                if (checker3[1]) {
                    list.add("0302");
                }
                if (checker3[2]) {
                    list.add("0303");
                }
                if (checker3[3]) {
                    list.add("0304");
                }
                if (checker3[4]) {
                    list.add("0305");
                }
                if (checker3[5]) {
                    list.add("0306");
                }
            }
            if (list.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.DATA), list);
                setResult(1001, intent);
                onBackPressed();
            }
        });

        cb1 = (CheckBox) findViewById(R.id.activity_choose_finance_time_cb1);
        cb2 = (CheckBox) findViewById(R.id.activity_choose_finance_time_cb2);
        cb3 = (CheckBox) findViewById(R.id.activity_choose_finance_time_cb3);

        ll1 = (LinearLayout) findViewById(R.id.activity_choose_finance_times_ll1);
        ll2 = (LinearLayout) findViewById(R.id.activity_choose_finance_times_ll2);
        ll3 = (LinearLayout) findViewById(R.id.activity_choose_finance_times_ll3);
        ll4 = (LinearLayout) findViewById(R.id.activity_choose_finance_times_ll4);
        ll5 = (LinearLayout) findViewById(R.id.activity_choose_finance_times_ll5);

        view1 = findViewById(R.id.activity_choose_finance_times_view1);
        view2 = findViewById(R.id.activity_choose_finance_times_view2);
        view3 = findViewById(R.id.activity_choose_finance_times_view3);
        view4 = findViewById(R.id.activity_choose_finance_times_view4);
        view5 = findViewById(R.id.activity_choose_finance_times_view5);

        tv11 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv1);
        tv12 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv2);
        tv13 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv3);
        tv14 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv4);
        tv15 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv5);
        tv16 = (TextView) findViewById(R.id.activity_choose_finance_time_ll1_tv6);

        tv21 = (TextView) findViewById(R.id.activity_choose_finance_time_ll2_tv1);
        tv22 = (TextView) findViewById(R.id.activity_choose_finance_time_ll2_tv2);
        tv23 = (TextView) findViewById(R.id.activity_choose_finance_time_ll2_tv3);
        tv24 = (TextView) findViewById(R.id.activity_choose_finance_time_ll2_tv4);
        tv25 = (TextView) findViewById(R.id.activity_choose_finance_time_ll2_tv5);

        tv31 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv1);
        tv32 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv2);
        tv33 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv3);
        tv34 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv4);
        tv35 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv5);
        tv36 = (TextView) findViewById(R.id.activity_choose_finance_time_ll3_tv6);

        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv13.setOnClickListener(this);
        tv14.setOnClickListener(this);
        tv15.setOnClickListener(this);
        tv16.setOnClickListener(this);
        tv21.setOnClickListener(this);
        tv22.setOnClickListener(this);
        tv23.setOnClickListener(this);
        tv24.setOnClickListener(this);
        tv25.setOnClickListener(this);
        tv31.setOnClickListener(this);
        tv32.setOnClickListener(this);
        tv33.setOnClickListener(this);
        tv34.setOnClickListener(this);
        tv35.setOnClickListener(this);
        tv36.setOnClickListener(this);

        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra(getString(R.string.LIST));

        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            switch (code) {
                case "0101":
                    tvSetClickShow(tv11);
                    checker1[0] = true;
                    break;
                case "0102":
                    tvSetClickShow(tv12);
                    checker1[1] = true;
                    break;
                case "0103":
                    tvSetClickShow(tv13);
                    checker1[2] = true;
                    break;
                case "0104":
                    tvSetClickShow(tv14);
                    checker1[3] = true;
                    break;
                case "0105":
                    tvSetClickShow(tv15);
                    checker1[4] = true;
                    break;
                case "0106":
                    tvSetClickShow(tv16);
                    checker1[5] = true;
                    break;
                case "0201":
                    tvSetClickShow(tv21);
                    checker2[0] = true;
                    break;
                case "0202":
                    tvSetClickShow(tv22);
                    checker2[1] = true;
                    break;
                case "0203":
                    tvSetClickShow(tv23);
                    checker2[2] = true;
                    break;
                case "0204":
                    tvSetClickShow(tv24);
                    checker2[3] = true;
                    break;
                case "0205":
                    tvSetClickShow(tv25);
                    checker2[4] = true;
                    break;
                case "0301":
                    tvSetClickShow(tv31);
                    checker3[0] = true;
                    break;
                case "0302":
                    tvSetClickShow(tv32);
                    checker3[1] = true;
                    break;
                case "0303":
                    tvSetClickShow(tv33);
                    checker3[2] = true;
                    break;
                case "0304":
                    tvSetClickShow(tv34);
                    checker3[3] = true;
                    break;
                case "0305":
                    tvSetClickShow(tv35);
                    checker3[4] = true;
                    break;
                case "0306":
                    tvSetClickShow(tv36);
                    checker3[5] = true;
                    break;
            }
        }
        if (checker1[0] || checker1[1] || checker1[2] || checker1[3] || checker1[4] || checker1[5]) {
            cb1.setChecked(true);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        } else {
            cb1.setChecked(false);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
        if (checker2[0] || checker2[1] || checker2[2] || checker2[3] || checker2[4]) {
            cb2.setChecked(true);
            ll3.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
        } else {
            cb2.setChecked(false);
            ll3.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
        }
        if (checker3[0] || checker3[1] || checker3[2] || checker3[3] || checker3[4] || checker3[5]) {
            cb3.setChecked(true);
            ll4.setVisibility(View.VISIBLE);
            ll5.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            view5.setVisibility(View.VISIBLE);
        } else {
            cb3.setChecked(false);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
            view5.setVisibility(View.GONE);
        }
        initData = false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_choose_finance_time_ll1_tv1:
                if (cb1.isChecked()) {
                    if (checker1[0]) {
                        tvSetClickhide(tv11);
                    } else {
                        tvSetClickShow(tv11);
                    }
                    checker1[0] = !checker1[0];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll1_tv2:
                if (cb1.isChecked()) {
                    if (checker2[1]) {
                        tvSetClickhide(tv12);
                    } else {
                        tvSetClickShow(tv12);
                    }
                    checker1[1] = !checker1[1];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll1_tv3:
                if (cb1.isChecked()) {
                    if (checker1[2]) {
                        tvSetClickhide(tv13);
                    } else {
                        tvSetClickShow(tv13);
                    }
                    checker1[2] = !checker1[2];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll1_tv4:
                if (cb1.isChecked()) {
                    if (checker1[3]) {
                        tvSetClickhide(tv14);
                    } else {
                        tvSetClickShow(tv14);
                    }
                    checker1[3] = !checker1[3];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll1_tv5:
                if (cb1.isChecked()) {
                    if (checker1[4]) {
                        tvSetClickhide(tv15);
                    } else {
                        tvSetClickShow(tv15);
                    }
                    checker1[4] = !checker1[4];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll1_tv6:
                if (cb1.isChecked()) {
                    if (checker1[5]) {
                        tvSetClickhide(tv16);
                    } else {
                        tvSetClickShow(tv16);
                    }
                    checker1[5] = !checker1[5];
                    checkCB1();
                }
                break;
            case R.id.activity_choose_finance_time_ll2_tv1:
                if (cb2.isChecked()) {
                    if (checker2[0]) {
                        tvSetClickhide(tv21);
                    } else {
                        tvSetClickShow(tv21);
                    }
                    checker2[0] = !checker2[0];
                    checkCB2();
                }
                break;
            case R.id.activity_choose_finance_time_ll2_tv2:
                if (cb2.isChecked()) {
                    if (checker2[1]) {
                        tvSetClickhide(tv22);
                    } else {
                        tvSetClickShow(tv22);
                    }
                    checker2[1] = !checker2[1];
                    checkCB2();
                }
                break;
            case R.id.activity_choose_finance_time_ll2_tv3:
                if (cb2.isChecked()) {
                    if (checker2[2]) {
                        tvSetClickhide(tv23);
                    } else {
                        tvSetClickShow(tv23);
                    }
                    checker2[2] = !checker2[2];
                    checkCB2();
                }
                break;
            case R.id.activity_choose_finance_time_ll2_tv4:
                if (cb2.isChecked()) {
                    if (checker2[3]) {
                        tvSetClickhide(tv24);
                    } else {
                        tvSetClickShow(tv24);
                    }
                    checker2[3] = !checker2[3];
                    checkCB2();
                }
                break;
            case R.id.activity_choose_finance_time_ll2_tv5:
                if (cb2.isChecked()) {
                    if (checker2[4]) {
                        tvSetClickhide(tv25);
                    } else {
                        tvSetClickShow(tv25);
                    }
                    checker2[4] = !checker2[4];
                    checkCB2();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv1:
                if (cb3.isChecked()) {
                    if (checker3[0]) {
                        tvSetClickhide(tv31);
                    } else {
                        tvSetClickShow(tv31);
                    }
                    checker3[0] = !checker3[0];
                    checkCB3();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv2:
                if (cb3.isChecked()) {
                    if (checker3[1]) {
                        tvSetClickhide(tv32);
                    } else {
                        tvSetClickShow(tv32);
                    }
                    checker3[1] = !checker3[1];
                    checkCB3();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv3:
                if (cb3.isChecked()) {
                    if (checker3[2]) {
                        tvSetClickhide(tv33);
                    } else {
                        tvSetClickShow(tv33);
                    }
                    checker3[2] = !checker3[2];
                    checkCB3();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv4:
                if (cb3.isChecked()) {
                    if (checker3[3]) {
                        tvSetClickhide(tv34);
                    } else {
                        tvSetClickShow(tv34);
                    }
                    checker3[3] = !checker3[3];
                    checkCB3();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv5:
                if (cb3.isChecked()) {
                    if (checker3[4]) {
                        tvSetClickhide(tv35);
                    } else {
                        tvSetClickShow(tv35);
                    }
                    checker3[4] = !checker3[4];
                    checkCB3();
                }
                break;
            case R.id.activity_choose_finance_time_ll3_tv6:
                if (cb3.isChecked()) {
                    if (checker3[5]) {
                        tvSetClickhide(tv36);
                    } else {
                        tvSetClickShow(tv36);
                    }
                    checker3[5] = !checker3[5];
                    checkCB3();
                }
                break;
        }
    }

    private void checkCB1() {
        if (checker1[0] || checker1[1] || checker1[2] || checker1[3] || checker1[4] || checker1[5]) {
            return;
        }
        cb1.setChecked(false);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        initClick();
    }

    private void checkCB2() {
        if (checker2[0] || checker2[1] || checker2[2] || checker2[3] || checker2[4]) {
            return;
        }
        cb2.setChecked(false);
        ll3.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        initClick();
    }

    private void checkCB3() {
        if (checker3[0] || checker3[1] || checker3[2] || checker3[3] || checker3[4] || checker3[5]) {
            return;
        }
        cb3.setChecked(false);
        ll4.setVisibility(View.GONE);
        ll5.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        view5.setVisibility(View.GONE);
        initClick();
    }

    private void initClick() {
        if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked()) {
            saveView.setTextColor(ContextCompat.getColor(this, R.color.color_959dce));
        } else {
            saveView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (initData) {//初始化數據
            return;
        }

        initClick();
        switch (buttonView.getId()) {
            case R.id.activity_choose_finance_time_cb1:

                if (isChecked) {

                    cb1.setChecked(true);
                    ll1.setVisibility(View.VISIBLE);
                    ll2.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);

                    tvSetClickShow(tv11);
                    tvSetClickShow(tv12);
                    tvSetClickShow(tv13);
                    tvSetClickShow(tv14);
                    tvSetClickShow(tv15);
                    tvSetClickShow(tv16);

                    checker1 = new boolean[]{true, true, true, true, true, true};
                } else {

                    cb1.setChecked(false);
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);

                    tvSetClickhide(tv11);
                    tvSetClickhide(tv12);
                    tvSetClickhide(tv13);
                    tvSetClickhide(tv14);
                    tvSetClickhide(tv15);
                    tvSetClickhide(tv16);
                }

                break;
            case R.id.activity_choose_finance_time_cb2:

                if (isChecked) {

                    cb2.setChecked(true);
                    ll3.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);

                    tvSetClickShow(tv21);
                    tvSetClickShow(tv22);
                    tvSetClickShow(tv23);
                    tvSetClickShow(tv24);
                    tvSetClickShow(tv25);

                    checker2 = new boolean[]{true, true, true, true, true};
                } else {

                    cb2.setChecked(false);
                    ll3.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);

                    tvSetClickhide(tv21);
                    tvSetClickhide(tv22);
                    tvSetClickhide(tv23);
                    tvSetClickhide(tv24);
                    tvSetClickhide(tv25);
                }

                break;
            case R.id.activity_choose_finance_time_cb3:

                if (isChecked) {

                    cb3.setChecked(true);
                    ll4.setVisibility(View.VISIBLE);
                    ll5.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);

                    tvSetClickShow(tv31);
                    tvSetClickShow(tv32);
                    tvSetClickShow(tv33);
                    tvSetClickShow(tv34);
                    tvSetClickShow(tv35);
                    tvSetClickShow(tv36);

                    checker3 = new boolean[]{true, true, true, true, true, true};
                } else {

                    cb3.setChecked(false);
                    ll4.setVisibility(View.GONE);
                    ll5.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);

                    tvSetClickhide(tv31);
                    tvSetClickhide(tv32);
                    tvSetClickhide(tv33);
                    tvSetClickhide(tv34);
                    tvSetClickhide(tv35);
                    tvSetClickhide(tv36);
                }

                break;
        }
    }

    public void tvSetClickShow(TextView view) {
        view.setTextColor(ContextCompat.getColor(this, R.color.white));
        view.setBackground(getResources().getDrawable(R.drawable.tx_bg_blue_reverse));
    }

    public void tvSetClickhide(TextView view) {
        view.setTextColor(ContextCompat.getColor(this, R.color.color_959dce));
        view.setBackgroundColor(Color.TRANSPARENT);
    }
}
