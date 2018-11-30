package com.buyint.mergerbot.UIs.loginAndRegister.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.ForgetPasswordActivity;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.RegisterLoginActivity;
import com.buyint.mergerbot.Utility.MatchesUtils;
import com.buyint.mergerbot.base.BaseFragment;
import com.buyint.mergerbot.dto.LoginDataCache;
import com.buyint.mergerbot.stroage.DataCache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by CXC on 2018/4/2
 */

public class LoginFragment1 extends BaseFragment {

    private LoginFragment1Listener listener;
    private EditText etPhone2;
    private TextView tv1;
    private TextView etView;
    private boolean canClick = false;
    private LinearLayout ll2;
    private LinearLayout ll1;
    private EditText etPhone;
    private EditText etPassWord;
    private TextView tvLogin;
    public int nowState = 0;
    public CheckBox checkBox;
    private ImageView ivDown;
    private ArrayList<String> stringKey;

    public LoginFragment1() {
    }

    @SuppressLint("ValidFragment")
    public LoginFragment1(LoginFragment1Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_1, container, false);

        checkBox = view.findViewById(R.id.fragment_login_1_cb);
        ll1 = view.findViewById(R.id.fragment_login_1_ll1);
        ll2 = view.findViewById(R.id.fragment_login_1_ll2);
        TextView smsLogin = view.findViewById(R.id.fragment_login_1_sms_login);
        TextView accountLogin = view.findViewById(R.id.fragment_login_2_account_login);
        TextView linkedinLogin1 = view.findViewById(R.id.fragment_login_1_linkedin);
        TextView linkedinLogin2 = view.findViewById(R.id.fragment_login_2_linkedin);
        tvLogin = view.findViewById(R.id.fragment_login_1_tv_login);

        etPhone = view.findViewById(R.id.fragment_login_1_et_phone1);
        etPassWord = view.findViewById(R.id.fragment_login_1_et_password);
        etView = view.findViewById(R.id.activity_login_tv);

        etPassWord.setTypeface(etPhone.getTypeface());

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(etPhone.getText().toString()) && !TextUtils.isEmpty(etPassWord.getText().toString())) {
                    tvLogin.setVisibility(View.VISIBLE);
                } else {
                    tvLogin.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    etPassWord.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(etPhone.getText().toString()) && !TextUtils.isEmpty(etPassWord.getText().toString())) {
                    tvLogin.setVisibility(View.VISIBLE);
                } else {
                    tvLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.checkbox_button);
        drawable.setBounds(0, 0, 60, 60);
        checkBox.setCompoundDrawables(drawable, null, null, null);

        ivDown = view.findViewById(R.id.fragment_login_1_down);

        LoginDataCache login_list = DataCache.instance.getCacheData(getString(R.string.buyint), getString(R.string.login_map));
        if (login_list == null) {
            ivDown.setVisibility(View.GONE);
        } else {
            etPhone.setText(login_list.account);
            etPassWord.setText(login_list.map.get(login_list.account));
            if (login_list.map.size() > 0) {
                stringKey = new ArrayList<>();
                Iterator<Map.Entry<String, String>> iterator = login_list.map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    stringKey.add(next.getKey());
                }
            }
        }

        ivDown.setOnClickListener(v -> {

            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (input.isActive() && getActivity().getCurrentFocus() != null) {
                if (getActivity().getCurrentFocus().getWindowToken() != null) {
                    input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            etPhone.clearFocus();
            etPassWord.clearFocus();

            new Handler().postDelayed(() -> {
                if (login_list != null) {
                    ListPopupWindow pop = new ListPopupWindow(getActivity());
                    pop.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringKey));
                    pop.setOnItemClickListener((parent, view1, position, id) -> {
                        etPhone.setText(stringKey.get(position));
                        etPassWord.setText(login_list.map.get(stringKey.get(position)));
                        pop.dismiss();
                    });

                    pop.setAnchorView(etPhone);
                    pop.setVerticalOffset(50);
                    pop.setModal(false);
                    pop.show();
                }
            }, 500);
        });

        view.findViewById(R.id.fragment_login_1_register).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RegisterLoginActivity.class));
            getActivity().finish();
        });
        view.findViewById(R.id.fragment_login_1_forget_password).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ForgetPasswordActivity.class));
        });
        smsLogin.setOnClickListener(v -> {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            nowState = 1;
            etView.setText(getString(R.string.phone_call_me_up));
        });
        accountLogin.setOnClickListener(v -> {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            nowState = 0;
            etView.setText(getString(R.string.enter_your_account_and_wake_me_up));
        });

        linkedinLogin1.setOnClickListener(v -> {
            if (listener != null) {
                listener.linkedInLogin();
            }
        });
        linkedinLogin2.setOnClickListener(v -> {
            if (listener != null) {
                listener.linkedInLogin();
            }
        });
        etPhone2 = view.findViewById(R.id.fragment_login_1_et_phone2);
        tv1 = view.findViewById(R.id.fragment_login_1_tv);
        tv1.setVisibility(View.GONE);

        tv1.setOnClickListener(v -> {
            if (MatchesUtils.validatePhoneNumber(etPhone2.getText().toString()) || MatchesUtils.validateEmail(etPhone2.getText().toString())) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (input.isActive() && getActivity().getCurrentFocus() != null) {
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                if (listener != null) {
                    listener.clickNext(etPhone2.getText().toString(), tv1);
                }
            } else {
                showToast(getString(R.string.please_enter_right_phone));
            }
        });

        etPhone2.addTextChangedListener(new TextWatcher() {
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

        tvLogin.setOnClickListener(v -> {
            if (listener != null) {
                listener.login(etPhone.getText().toString(), etPassWord.getText().toString(), checkBox.isChecked());
            }
        });

        return view;
    }

    public interface LoginFragment1Listener {
        void clickNext(String phone, View... view);

        void login(String phone, String password, boolean checked);

        void linkedInLogin();
    }
}
