package com.buyint.mergerbot.UIs.user.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.AccountMergerRequest;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.SmsResponse;
import com.buyint.mergerbot.interfaces.IAccountMerger;
import com.buyint.mergerbot.interfaces.ILoginGetSms;
import com.buyint.mergerbot.presenter.AccountBinding2Presenter;

import org.jetbrains.annotations.NotNull;

/**
 * Created by huheming on 2018/7/4
 */

public class AccountBinding2Activity extends BaseActivity implements ILoginGetSms, IAccountMerger {

    private LinearLayout phoneLL, emailLL;
    private TextView ivRight, phone, email, getCode;
    private EditText etVerifyCode, password, newPassword;
    private boolean canClick = false;
    private AccountBinding2Presenter presenter;
    private String type;
    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_msg2);

        presenter = new AccountBinding2Presenter(this, this);

        ((TextView) findViewById(R.id.toorbar_title)).setText(getString(R.string.set_password));
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());
        ivRight = (TextView) findViewById(R.id.toolbar_right);
        Drawable drawable = getResources().getDrawable(R.mipmap.duigou);
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f));
        ivRight.setCompoundDrawables(null, null, drawable, null);
        ivRight.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(etVerifyCode.getText().toString().trim()) && !TextUtils.isEmpty(password.getText().toString().trim()) && !TextUtils.isEmpty(newPassword.getText().toString().trim()) && (password.getText().toString().trim().equals(newPassword.getText().toString().trim()))) {
                showLoadingFragment(R.id.activity_verification_msg2_fl);
                AccountMergerRequest request = new AccountMergerRequest();
                if (type.equals(getString(R.string.TYPE_1))) {
                    request.mergeType = getString(R.string.EMAIL);
                } else if (type.equals(getString(R.string.TYPE_0))) {
                    request.mergeType = getString(R.string.PHONE);
                }
                request.mergedAccount = data;
                request.newPassword = password.getText().toString().trim();
                request.verificationCode = etVerifyCode.getText().toString().trim();
                presenter.accountMerger(request);
            }
        });

        phone = (TextView) findViewById(R.id.activity_verification_msg2_phone);
        email = (TextView) findViewById(R.id.activity_verification_msg2_email);
        phoneLL = (LinearLayout) findViewById(R.id.activity_verification_msg2_phone_ll);
        emailLL = (LinearLayout) findViewById(R.id.activity_verification_msg2_email_ll);

        etVerifyCode = (EditText) findViewById(R.id.activity_verification_msg2_verify_code);
        password = (EditText) findViewById(R.id.activity_verification_msg2_password);
        newPassword = (EditText) findViewById(R.id.activity_verification_msg2_new_password);
        getCode = (TextView) findViewById(R.id.activity_verification_msg2_get_verify_code);

        LoginResponse response = DBUtilsKt.getLoginResponse(this);

        data = getIntent().getStringExtra(getString(R.string.DATA));
        if (data.contains("@")) {
            email.setText(data);
            type = getString(R.string.TYPE_1);
            if (TextUtils.isEmpty(response.model.phone)) {
                phoneLL.setVisibility(View.GONE);
            } else {
                phone.setText(response.model.phone);
            }
        } else {
            phone.setText(data);
            type = getString(R.string.TYPE_0);
            if (response.model.email == null || response.model.email.size() == 0) {
                emailLL.setVisibility(View.GONE);
            } else {
                email.setText(response.model.email.get(0));
            }
        }

        getCode.setOnClickListener(v -> {
            if (canClick) {
                showLoadingFragment(R.id.activity_verification_msg2_fl);
                presenter.accountBindingGetSms(type, data);
            }
        });

        handler.sendEmptyMessage(60);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != 0) {
                getCode.setTextColor(ContextCompat.getColor(AccountBinding2Activity.this, R.color.color_b3b3b3));
                getCode.setText(getString(R.string.after_can_resend_before) + msg.what + getString(R.string.after_can_resend_after));
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else {
                canClick = true;
                getCode.setTextColor(ContextCompat.getColor(AccountBinding2Activity.this, R.color.color_959dce));
                getCode.setText(getResources().getString(R.string.resend));
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.destory();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void loginGetMsmSuccess(@NotNull SmsResponse response) {
        canClick = false;
        removeLoadingFragment();
        handler.sendEmptyMessage(60);
    }

    @Override
    public void loginGetMsmFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }

    @Override
    public void accountMergerSuccess(@NotNull LoginResponse response) {
        DBUtilsKt.saveLoginResponse(this, response);
        removeLoadingFragment();
        setResult(1003);
        finish();
    }

    @Override
    public void accountMergerFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }
}
