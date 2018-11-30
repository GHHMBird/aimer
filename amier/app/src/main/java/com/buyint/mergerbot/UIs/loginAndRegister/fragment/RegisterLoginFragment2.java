package com.buyint.mergerbot.UIs.loginAndRegister.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.LoginActivity;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.RegisterLoginActivity;
import com.buyint.mergerbot.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by CXC on 2018/4/2
 */

public class RegisterLoginFragment2 extends BaseFragment {

    private LoginFragment2Listener listener;
    private boolean canClick = true;
    private EditText etView;
    private TextView tvTime, tvResend;
    private int chooseColor;
    private TextView tvTop;
    private Disposable disposable;

    public RegisterLoginFragment2() {
    }

    @SuppressLint("ValidFragment")
    public RegisterLoginFragment2(LoginFragment2Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_login_2, container, false);

        tvTop = view.findViewById(R.id.fragment_register_login_2_tv_top);

        view.findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_white_right_add_rl).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_white_right_search_rl).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.toolbar_white_title)).setText(getString(R.string.verify_code));
        view.findViewById(R.id.toolbar_white_back).setOnClickListener(v -> {
            if (listener != null) {
                listener.goBackPage();
            }
        });

        etView = view.findViewById(R.id.fragment_register_login_2_icv);
        tvTime = view.findViewById(R.id.fragment_register_login_2_time);
        chooseColor = tvTime.getCurrentTextColor();
        tvResend = view.findViewById(R.id.fragment_register_login_2_resend);
        tvResend.setOnClickListener(v -> {
            if (canClick) {
                canClick = false;
                etView.setText("");
                rxCountDown();
                if (listener != null) {
                    listener.reSendCode();
                }
            }
        });

        etView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etView.getText().toString().trim().length() == 6) {
                    InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (input.isActive() && getActivity().getCurrentFocus() != null) {
                        if (getActivity().getCurrentFocus().getWindowToken() != null) {
                            input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    if (listener != null) {
                        listener.getCode(etView.getText().toString().trim());//获取到验证码
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    public void startCountDown() {
        if (canClick) {
            canClick = false;
            etView.setText("");
            Activity ac = getActivity();
            if (ac instanceof RegisterLoginActivity) {
                String phoneOrAddress = ((RegisterLoginActivity) ac).phoneOrAddress;
                tvTop.setText(tvTop.getText().toString().trim() + phoneOrAddress.substring(0, 3) + "******");
            } else if (ac instanceof LoginActivity) {
                String phoneOrAddress = ((LoginActivity) ac).phoneOrAddress;
                tvTop.setText(tvTop.getText().toString().trim() + phoneOrAddress.substring(0, 3) + "******");
            }
            rxCountDown();
        }
    }

    public void rxCountDown() {
        Observable.interval(0, 1, TimeUnit.SECONDS).take(61).map(aLong -> 60 - aLong).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                if (getActivity() != null) {
                    tvTime.setText(aLong + "s");
                    if (aLong == 0) {
                        tvTime.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_999999));
                    } else {
                        tvTime.setTextColor(chooseColor);
                    }
                    tvResend.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_999999));
                    tvResend.setBackgroundResource(R.drawable.md_transparent);
                    Activity ac = getActivity();
                    if (ac instanceof RegisterLoginActivity) {
                        ((RegisterLoginActivity) ac).getSms = false;
                    } else if (ac instanceof LoginActivity) {
                        ((LoginActivity) ac).getSms = false;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                if (getActivity() != null) {
                    canClick = true;
                    tvResend.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_1f0398));
                    tvResend.setBackgroundResource(R.drawable.tx_bg_blue_reverse);
                    Activity ac = getActivity();
                    if (ac instanceof RegisterLoginActivity) {
                        ((RegisterLoginActivity) ac).getSms = true;
                    } else if (ac instanceof LoginActivity) {
                        ((LoginActivity) ac).getSms = true;
                    }
                }
            }
        });
    }

    public interface LoginFragment2Listener {
        void getCode(String code);

        void goBackPage();

        void reSendCode();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
