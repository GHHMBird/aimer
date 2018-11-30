package com.buyint.mergerbot.UIs.match.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.UnitNumIndexBean;
import com.buyint.mergerbot.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/11.
 */

public class OtherTargetActivity extends BaseActivity {

    private ArrayList<UnitNumIndexBean> list = new ArrayList<>();
    private LinearLayout llAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_target);

        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.activity_other_target_save).setOnClickListener(v -> {
            int childCount = llAdd.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = llAdd.getChildAt(i);
                EditText etName = (EditText) view.findViewById(R.id.activity_other_target_name);
                EditText etNum = (EditText) view.findViewById(R.id.activity_other_target_num);
                EditText etUnit = (EditText) view.findViewById(R.id.activity_other_target_unit);

                if (TextUtils.isEmpty(etName.getText().toString()) && TextUtils.isEmpty(etNum.getText().toString()) && TextUtils.isEmpty(etUnit.getText().toString())) {
                    continue;
                }
                UnitNumIndexBean bean = new UnitNumIndexBean();
                bean.index_name = etName.getText().toString();
                bean.number = Double.parseDouble(etNum.getText().toString());
                bean.unit = etUnit.getText().toString();
                list.add(bean);
            }
            if (list.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.LIST), list);
                setResult(1001, intent);
                finish();
            }
        });

        llAdd = (LinearLayout) findViewById(R.id.activity_other_target_ll);
        TextView tvAdd = (TextView) findViewById(R.id.activity_other_target_tv);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItems(true);
            }
        });

        addItems(false);

        //恢复数据
        ArrayList<UnitNumIndexBean> list = (ArrayList<UnitNumIndexBean>) getIntent().getSerializableExtra(getString(R.string.LIST));
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                View view = llAdd.getChildAt(0);
                UnitNumIndexBean u = list.get(0);
                setViewData(view, u);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        View view = llAdd.getChildAt(0);
                        UnitNumIndexBean u = list.get(0);
                        setViewData(view, u);
                    } else {
                        View view = addItems(true);
                        UnitNumIndexBean u = list.get(i);
                        setViewData(view, u);
                    }
                }
            }
        }
    }

    public void setViewData(View view, UnitNumIndexBean u) {
        EditText etName = (EditText) view.findViewById(R.id.activity_other_target_name);
        EditText etNum = (EditText) view.findViewById(R.id.activity_other_target_num);
        EditText etUnit = (EditText) view.findViewById(R.id.activity_other_target_unit);

        etName.setText(u.index_name);
        etNum.setText("" + u.number);
        etUnit.setText(u.unit);
    }

    private View addItems(boolean canClose) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_other_target_activity, null);
        ImageView x = (ImageView) inflate.findViewById(R.id.item_other_target_activity_x);
        if (canClose) {
            x.setVisibility(View.VISIBLE);
        } else {
            x.setVisibility(View.GONE);
        }
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAdd.removeView(inflate);
            }
        });
        llAdd.addView(inflate);
        return inflate;
    }

    @Override
    protected void onStop() {
        super.onStop();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
