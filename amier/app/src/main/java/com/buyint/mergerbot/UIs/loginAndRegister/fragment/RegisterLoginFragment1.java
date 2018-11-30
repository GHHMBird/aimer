package com.buyint.mergerbot.UIs.loginAndRegister.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.buyint.mergerbot.UIs.setting.TextActivity;
import com.buyint.mergerbot.base.BaseFragment;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.LoginActivity;
import com.buyint.mergerbot.Utility.MatchesUtils;

/**
 * Created by CXC on 2018/4/2
 */

public class RegisterLoginFragment1 extends BaseFragment {

    private LoginFragment1Listener listener;
    private EditText et1;
    private TextView tv1;

    public RegisterLoginFragment1() {
    }

    @SuppressLint("ValidFragment")
    public RegisterLoginFragment1(LoginFragment1Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_login_1, container, false);

        view.findViewById(R.id.fragment_register_login_1_aimer_agreement).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TextActivity.class);
            intent.putExtra(getString(R.string.TITLE), getString(R.string.aimer_agreement));
            intent.putExtra(getString(R.string.TYPE), 0);
            startActivity(intent);
        });
        view.findViewById(R.id.fragment_register_login_1_privacy_polity).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TextActivity.class);
            intent.putExtra(getString(R.string.TITLE), getString(R.string.privacy_policy));
            intent.putExtra(getString(R.string.TYPE), 1);
            startActivity(intent);
        });

        et1 = (EditText) view.findViewById(R.id.fragment_register_login_1_et);
        tv1 = (TextView) view.findViewById(R.id.fragment_register_login_1_tv);
        tv1.setVisibility(View.GONE);
        view.findViewById(R.id.fragment_register_login_1_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MatchesUtils.validatePhoneNumber(et1.getText().toString()) || MatchesUtils.validateEmail(et1.getText().toString())) {
                    InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (input.isActive() && getActivity().getCurrentFocus() != null) {
                        if (getActivity().getCurrentFocus().getWindowToken() != null) {
                            input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    if (listener != null) {
                        listener.clickNext(et1.getText().toString(), tv1);
                    }
                } else {
                    showToast(getString(R.string.please_enter_right_phone));
                }
            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().trim().length();
                if (length > 0) {
                    tv1.setVisibility(View.VISIBLE);
                } else {
                    tv1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    public interface LoginFragment1Listener {
        void clickNext(String phone, View... view);
    }
}
