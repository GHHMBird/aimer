package com.buyint.mergerbot.UIs.loginAndRegister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.adapter.RegistrationActivityViewPager;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.LoginFragment1;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.RegisterLoginFragment2;
import com.buyint.mergerbot.UIs.main.activity.HomeActivity;
import com.buyint.mergerbot.Utility.LinkedInUtils;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.LinkedinRequest;
import com.buyint.mergerbot.dto.LoginDataCache;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.PasswordLoginRequest;
import com.buyint.mergerbot.dto.RegisterRequest;
import com.buyint.mergerbot.dto.SmsResponse;
import com.buyint.mergerbot.interfaces.ILinkedInLogin;
import com.buyint.mergerbot.interfaces.ILoginGetSms;
import com.buyint.mergerbot.interfaces.IPasswordLogin;
import com.buyint.mergerbot.interfaces.ISmsLogin;
import com.buyint.mergerbot.presenter.LoginActivityPresenter;
import com.buyint.mergerbot.stroage.DataCache;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.CustomVerticalViewPager;
import com.buyint.mergerbot.view.ViewPagerTransformer;
import com.linkedin.platform.LISessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements ILoginGetSms, ISmsLogin, ILinkedInLogin, IPasswordLogin {

    private CustomVerticalViewPager viewPager;
    private long exitTime = 0;
    public boolean checked;
    private boolean isDialogShow = false;
    public String phoneOrAddress;
    private String poa;
    private ViewPagerTransformer translateAnimation;
    private RegistrationActivityViewPager adapter;
    private double maxPage;
    private RegisterLoginFragment2 loginFragment2;
    public boolean getSms = true;
    private String password;
    private String code;
    private LoginActivityPresenter presenter;
    private boolean linkedInLogin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTitleWhiteAndTextBlank();

        presenter = new LoginActivityPresenter(this, this, this, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager = findViewById(R.id.activity_login_viewpager);

        ArrayList<Fragment> fragments = initViewPager();

        translateAnimation = new ViewPagerTransformer();

        viewPager.setPageTransformer(true, translateAnimation);
        adapter = new RegistrationActivityViewPager(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position >= maxPage) {
                    maxPage = position;
                }
                translateAnimation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    switch (viewPager.getCurrentItem()) {
                        case 0:
                            break;
                        case 1:
                            break;
                    }
                }
            }
        });

    }

    private void linkedinLogin() {
        LinkedInUtils.linkedinLogin(this, new LinkedInUtils.LinkedinUtilsListener() {
            @Override
            public void success(LinkedinRequest req) {
                presenter.linkedInLogin(req);
            }

            @Override
            public void fail() {
                removeLoadingFragment();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (linkedInLogin) {
            LISessionManager.getInstance(this).onActivityResult(this, requestCode, resultCode, data);
        }
        if (requestCode == 10084 && resultCode == 10085) {
            setResult(10086);
            finish();
        }
    }

    private void passwordLogin() {
        PasswordLoginRequest request = new PasswordLoginRequest();
        request.account = phoneOrAddress;
        request.password = password;
        presenter.passwordLogin(request);
    }

    private void vertifySms() {
        RegisterRequest request = new RegisterRequest();
        request.phoneNumber = phoneOrAddress;
        request.verificationCode = code;
        presenter.smsLogin(request);
    }

    private ArrayList<Fragment> initViewPager() {
        ArrayList<Fragment> list = new ArrayList<>();
        LoginFragment1 loginFragment1 = new LoginFragment1(new LoginFragment1.LoginFragment1Listener() {

            @Override
            public void clickNext(String phone, View[] v) {
                LoginActivity.this.phoneOrAddress = phone;

                //判断是电话还是邮箱
                if (phoneOrAddress.contains("@")) {
                    //邮箱
                    poa = getString(R.string.TYPE_1);
                } else {
                    //电话
                    poa = getString(R.string.TYPE_0);
                }

                if (getSms) {
                    showLoadingFragment(R.id.activity_login_fl);
                    presenter.loginGetSms(poa, phoneOrAddress);
                } else {
                    onPageSelected(1);
                }
            }

            @Override
            public void login(String phone, String password, boolean checked) {
                LoginActivity.this.checked = checked;
                LoginActivity.this.phoneOrAddress = phone;
                LoginActivity.this.password = password;
                showLoadingFragment(R.id.activity_login_fl);
                passwordLogin();
            }

            @Override
            public void linkedInLogin() {
                showLoadingFragment(R.id.activity_login_fl);
                linkedInLogin = true;
                linkedinLogin();
            }
        });
        loginFragment2 = new RegisterLoginFragment2(new RegisterLoginFragment2.LoginFragment2Listener() {
            @Override
            public void getCode(String code) {
                LoginActivity.this.code = code;
                showLoadingFragment(R.id.activity_login_fl);
                vertifySms();
            }

            @Override
            public void goBackPage() {
                viewPager.setCurrentItem(0, true);
            }

            @Override
            public void reSendCode() {
                presenter.loginGetSms(poa, phoneOrAddress);
            }
        });
        list.add(loginFragment1);

        return list;
    }

    public void onPageSelected(int page) {
        if (page > maxPage) {
            maxPage = page;
        }
        translateAnimation.setCurrentItem(page);
        viewPager.setCurrentItem(page, true);
    }

    @Override
    public void onBackPressed() {
        if (!isDialogShow && viewPager.getCurrentItem() == 1) {
            exit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            if (isDialogShow) {
                isDialogShow = false;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) < 2000) {
            isDialogShow = true;
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder.positiveText(R.string.alright);
            builder.negativeText(R.string.cancel);
            builder.positiveColorRes(R.color.colorBlack);
            builder.negativeColorRes(R.color.colorBlack);
            builder.content(R.string.sure_to_quit_this);
            builder.onNegative((dialog, which) -> {
                dialog.dismiss();
                exitTime = 0;
                isDialogShow = false;
            });
            builder.onPositive((dialog, which) -> {
                dialog.dismiss();
                finish();
            });
            builder.build().show();
        } else {
            exitTime = System.currentTimeMillis();
        }
    }

//    public void setTitles(int position) {
//        if (position == 0) {
//            mTvTitle.setText(getString(R.string.enter_your_account_and_wake_me_up));
//        } else if (position == 1) {
//            mTvTitle.setText(getString(R.string.phone_call_me_up));
//        }
//    }

    @Override
    public void loginGetMsmSuccess(@NotNull SmsResponse response) {
        boolean hasPhone = response.data.phoneNumberExit;

        if (hasPhone) {
            if (!adapter.contains(loginFragment2)) {
                adapter.addAdapter(loginFragment2);
            }
            onPageSelected(1);
            loginFragment2.startCountDown();

        } else {
            showToast(getString(R.string.please_register));
        }
        removeLoadingFragment();
    }

    @Override
    public void loginGetMsmFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.destory();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void smsLoginSuccess(@NotNull LoginResponse response) {
        DBUtilsKt.saveLoginResponse(this, response);

        if ("2".equals(response.model.userMessageLevel)) {//没有填写个人偏好

            JPushInterface.setAlias(this, 1, response.model.uuid);

            PreferencesKeeper.putBoolean(LoginActivity.this, getString(R.string.FIRST_REGISTER), false);
            setResult(10086);
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

           /* Intent intent = new Intent(LoginActivity.this, UserInfoQAActivity.class);
            startActivityForResult(intent, 10084);*/

        } else if ("3".equals(response.model.userMessageLevel)) {//信息齐全的登录

            JPushInterface.setAlias(this, 1, response.model.uuid);

            PreferencesKeeper.putBoolean(LoginActivity.this, getString(R.string.FIRST_REGISTER), false);
            setResult(10086);
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

        } else {//0或者1都是没有密码的账号

            Intent intent = new Intent(LoginActivity.this, RegisterLoginActivity.class);
            intent.putExtra(getString(R.string.DATA), phoneOrAddress);
            intent.putExtra(getString(R.string.TYPE), 3);
            startActivity(intent);

        }
        removeLoadingFragment();
    }

    @Override
    public void smsLoginFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }

    @Override
    public void linkedInLoginSuccess(@NotNull LoginResponse response) {
        response.model.loginCount = response.model.linkedInId;
        DBUtilsKt.saveLoginResponse(this, response);

        removeLoadingFragment();
        if ("2".equals(response.model.userMessageLevel)) {

            JPushInterface.setAlias(this, 1, response.model.uuid);

            setResult(10086);
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));

            /*Intent intent = new Intent(LoginActivity.this, UserInfoQAActivity.class);
            intent.putExtra(getString(R.string.NAME), response.model.nickName);
            startActivityForResult(intent, 10084);*/
        } else if ("3".equals(response.model.userMessageLevel)) {

            JPushInterface.setAlias(this, 1, response.model.uuid);

            setResult(10086);
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    @Override
    public void linkedInLoginFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
        removeLoadingFragment();
    }

    @Override
    public void passwordLoginSuccess(@NotNull LoginResponse response) {

        LoginDataCache login_list = DataCache.instance.getCacheData(getString(R.string.buyint), getString(R.string.login_map));
        if (login_list == null) {
            login_list = new LoginDataCache();
            login_list.account = phoneOrAddress;
            login_list.map = new HashMap<>();
            if (checked) {
                login_list.map.put(phoneOrAddress, password);
            } else {
                login_list.map.put(phoneOrAddress, "");
            }
        } else {
            login_list.account = phoneOrAddress;
            if (checked) {
                login_list.map.put(phoneOrAddress, password);
            } else {
                login_list.map.put(phoneOrAddress, "");
            }
        }
        response.model.loginCount = phoneOrAddress;
        DataCache.instance.saveCacheData(getString(R.string.buyint), getString(R.string.login_map), login_list);

        DBUtilsKt.saveLoginResponse(this, response);

        if ("2".equals(response.model.userMessageLevel)) {//没有填写个人偏好

            JPushInterface.setAlias(this, 1, response.model.uuid);

            PreferencesKeeper.putBoolean(LoginActivity.this, "first", false);
            setResult(10086);
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

           /* Intent intent = new Intent(LoginActivity.this, UserInfoQAActivity.class);
            startActivityForResult(intent, 10084);*/

        } else if ("3".equals(response.model.userMessageLevel)) {//信息齐全的登录

            JPushInterface.setAlias(this, 1, response.model.uuid);

            PreferencesKeeper.putBoolean(LoginActivity.this, "first", false);
            setResult(10086);
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

        } else {//0或者1都是没有密码的账号

            Intent intent = new Intent(LoginActivity.this, RegisterLoginActivity.class);
            intent.putExtra(getString(R.string.DATA), phoneOrAddress);
            intent.putExtra(getString(R.string.TYPE), 3);
            startActivity(intent);

        }
        removeLoadingFragment();
    }

    @Override
    public void passwordLoginFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }
}
