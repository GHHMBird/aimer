package com.buyint.mergerbot.UIs.loginAndRegister.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.buyint.mergerbot.base.BaseFragment;
import com.buyint.mergerbot.R;

/**
 * Created by CXC on 2018/5/14
 */

public class RegisterLoginFragment3 extends BaseFragment implements TextWatcher {

    private LoginFragment3Listener listener;
    private TextView tvNext;
    private TextView goto4;
    private EditText et2;
    private EditText et1;

    public RegisterLoginFragment3() {
    }

    @SuppressLint("ValidFragment")
    public RegisterLoginFragment3(LoginFragment3Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_login_3, container, false);
        et1 = view.findViewById(R.id.fragment_register_login_3_et_password1);
        et2 = view.findViewById(R.id.fragment_register_login_3_et_password2);
        goto4 = view.findViewById(R.id.fragment_register_login_3_goto4);
        et1.addTextChangedListener(this);
        et2.addTextChangedListener(this);
        tvNext = view.findViewById(R.id.fragment_register_login_3_tv);
        tvNext.setOnClickListener(v -> {
            if (et1.getText().toString().trim().equals(et2.getText().toString().trim())) {
                if (listener != null) {
                    listener.setPassWord(et1.getText().toString().trim());
                }
            } else {
                showToast(getString(R.string.twice_password_difference));
            }
        });
        goto4.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(et1.getText().toString().trim()) && et1.getText().toString().trim().equals(et2.getText().toString().trim())) {
                if (listener != null) {
                    listener.goToNextPage();
                }
            } else {
                showToast(getString(R.string.please_fill_in_your_password));
            }
        });
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString())) {
            tvNext.setVisibility(View.VISIBLE);
        } else {
            tvNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public interface LoginFragment3Listener {
        void setPassWord(String password);

        void goToNextPage();
    }

}
