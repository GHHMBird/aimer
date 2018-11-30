package com.buyint.mergerbot.UIs.loginAndRegister.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.adapter.RegistrationActivityViewPager;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.RegisterLoginFragment1;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.RegisterLoginFragment2;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.RegisterLoginFragment3;
import com.buyint.mergerbot.UIs.loginAndRegister.fragment.RegisterLoginFragment4;
import com.buyint.mergerbot.UIs.main.activity.HomeActivity;
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity;
import com.buyint.mergerbot.UIs.user.fragment.SetUserIconFragment;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.BooleanResponse;
import com.buyint.mergerbot.dto.LoginModel;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.PostUserInfoType3Request;
import com.buyint.mergerbot.dto.ProjectIdNameBean;
import com.buyint.mergerbot.dto.RegisterVerificationResponse;
import com.buyint.mergerbot.dto.SmsResponse;
import com.buyint.mergerbot.dto.StringResponse;
import com.buyint.mergerbot.interfaces.IPostUserInfoType3;
import com.buyint.mergerbot.interfaces.IRegisterGetSms;
import com.buyint.mergerbot.interfaces.IRegisterSetPassword;
import com.buyint.mergerbot.interfaces.IRegisterVerification;
import com.buyint.mergerbot.interfaces.IUserIconUpdate;
import com.buyint.mergerbot.presenter.RegisterLoginPresenter;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.CustomVerticalViewPager;
import com.buyint.mergerbot.view.ViewPagerTransformer;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by CXC on 2018/4/2
 */

public class RegisterLoginActivity extends BaseActivity implements IRegisterGetSms, IRegisterSetPassword, IRegisterVerification, IUserIconUpdate, IPostUserInfoType3 {

    private CustomVerticalViewPager viewPager;
    private long exitTime = 0;
    private boolean isDialogShow = false;
    public String phoneOrAddress;
    private String poa;
    private ViewPagerTransformer translateAnimation;
    private RegistrationActivityViewPager adapter;
    private double maxPage;
    private RegisterLoginFragment2 registerLoginFragment2;
    public boolean getSms = true;
    public String verificationCode;
    private RegisterLoginFragment3 registerLoginFragment3;
    private RegisterLoginFragment4 registerLoginFragment4;
    private String password;
    private int type;
    private boolean first = true;
    private RegisterLoginPresenter presenter;
    private Uri uri;
    private int CROP_PICTURE = 30;
    private File file;
    private Uri cropImageUri;
    private File finalPic;
    private ProjectIdNameBean companyNameBean = new ProjectIdNameBean();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTitleWhiteAndTextBlank();

        first = PreferencesKeeper.getTrueBoolean(this, getString(R.string.FIRST_REGISTER));

        presenter = new RegisterLoginPresenter(this, this, this, this, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        viewPager = findViewById(R.id.activity_register_login_viewpager);

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
                translateAnimation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    switch (viewPager.getCurrentItem()) {
                        case 0:

                            break;
                        case 1:
//                            if (!mTvTitle.getText().toString().contains(getString(R.string.send_to))) {
//                                mTvTitle.setText(getString(R.string.send_to) + phoneOrAddress);
//                            }
                            if (first) {
                                findViewById(R.id.activity_register_login_ps_bg).setVisibility(View.VISIBLE);
                                findViewById(R.id.activity_register_login_ps_bg).setOnClickListener(v -> {
                                    findViewById(R.id.activity_register_login_ps_bg).setVisibility(View.GONE);
                                    first = false;
                                    PreferencesKeeper.putBoolean(RegisterLoginActivity.this, getString(R.string.FIRST_REGISTER), false);
                                });
                                ImageView headIv = findViewById(R.id.activity_register_login_ps_head);
                                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                                        0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                                animation.setDuration(3000);
                                animation.setRepeatMode(Animation.RESTART);
                                animation.setRepeatCount(-1);
                                headIv.setAnimation(animation);
                                animation.start();
                            }
                            break;
                    }
                }
            }
        });

        type = getIntent().getIntExtra(getString(R.string.TYPE), -1);
        if (3 == type) {
            if (!adapter.contains(registerLoginFragment3)) {
                adapter.addAdapter(registerLoginFragment3);
                viewPager.setOffscreenPageLimit(3);
            }
            onPageSelected(1);
            phoneOrAddress = getIntent().getStringExtra(getString(R.string.DATA));
            viewPager.setOnTouchListener((v, event) -> true);
        }
    }

    private ArrayList<Fragment> initViewPager() {
        ArrayList<Fragment> list = new ArrayList<>();
        RegisterLoginFragment1 registerLoginFragment1 = new RegisterLoginFragment1((phone, v) -> {
            RegisterLoginActivity.this.phoneOrAddress = phone;

            //判断是电话还是邮箱
            if (phoneOrAddress.contains("@")) {
                //邮箱
                poa = getString(R.string.TYPE_1);
            } else {
                //电话
                poa = getString(R.string.TYPE_0);
            }

            if (getSms) {
                showLoadingFragment(R.id.activity_register_login_fl);
                presenter.registerGetSms(poa, phoneOrAddress);
            } else {
                onPageSelected(1);
            }

        });
        registerLoginFragment2 = new RegisterLoginFragment2(new RegisterLoginFragment2.LoginFragment2Listener() {

            @Override
            public void goBackPage() {
                viewPager.setCurrentItem(0, true);
            }

            @Override
            public void getCode(String code) {
                RegisterLoginActivity.this.verificationCode = code;
                showLoadingFragment(R.id.activity_register_login_fl);
                presenter.registerVerification(phoneOrAddress, verificationCode);
            }

            @Override
            public void reSendCode() {
                presenter.registerGetSms(poa, phoneOrAddress);
            }
        });
        registerLoginFragment3 = new RegisterLoginFragment3(new RegisterLoginFragment3.LoginFragment3Listener() {

            @Override
            public void setPassWord(String password) {
                RegisterLoginActivity.this.password = password;
                showLoadingFragment(R.id.activity_register_login_fl);
                presenter.registerSetPassword(password);
            }

            @Override
            public void goToNextPage() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                }
                if (!adapter.contains(registerLoginFragment4)) {
                    adapter.addAdapter(registerLoginFragment4);
                    viewPager.setOffscreenPageLimit(4);
                }
                onPageSelected(3);
            }
        });
        registerLoginFragment4 = new RegisterLoginFragment4(new RegisterLoginFragment4.LoginFragment4Listener() {

            @Override
            public void writeFinish(PostUserInfoType3Request request) {
                request.setPassword(password);
                showLoadingFragment(R.id.activity_register_login_fl);
                presenter.postUserInfoType3(request);
            }

            @Override
            public void setIcon() {
                SetUserIconFragment suif = new SetUserIconFragment(new SetUserIconFragment.SetUserIconFragmentListener() {
                    @Override
                    public void photoClick() {
                        //检查应用必备权限
                        RxPermissions rxPermissions = new RxPermissions(RegisterLoginActivity.this);
                        rxPermissions.request(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(aBoolean -> {
                                    if (aBoolean) {
                                        getPhoto();
                                    }
                                });
                    }

                    @Override
                    public void pictureClick() {
                        //检查应用必备权限
                        RxPermissions rxPermissions = new RxPermissions(RegisterLoginActivity.this);
                        rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(aBoolean -> {
                                    if (aBoolean) {
                                        getPicture();
                                    }
                                });
                    }

                });
                suif.show(getSupportFragmentManager(), "SetUserIconFragment");
            }

            @Override
            public void back() {
                onPageSelected(2);
            }

            @Override
            public void Match(int position) {
                Intent intent = new Intent(RegisterLoginActivity.this, QuickMatchActivity.class);
                switch (position) {
                    case 10:
                        intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_name));
                        startActivityForResult(intent, 10);
                        break;
                    case 11:
                        intent.putExtra(getString(R.string.TYPE), 19);
                        intent.putExtra(getString(R.string.DATA), companyNameBean);
                        startActivityForResult(intent, 11);
                        break;
                    case 12:
                        intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_division));
                        startActivityForResult(intent, 12);
                        break;
                    case 13:
                        intent.putExtra(getString(R.string.NAME), getString(R.string.write_company_position));
                        startActivityForResult(intent, 13);
                        break;
                    case 14:
                        intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_company_website));
                        startActivityForResult(intent, 14);
                        break;
                    case 15:
                        intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_company_email));
                        startActivityForResult(intent, 15);
                        break;
                }
            }
        });
        list.add(registerLoginFragment1);

        return list;
    }

    public void onPageSelected(int page) {
        translateAnimation.setCurrentItem(page);
        viewPager.setCurrentItem(page, true);
    }

    @Override
    public void onBackPressed() {
        if (!isDialogShow && viewPager.getCurrentItem() == 1 && 3 != type) {
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

    @Override
    public void registerGetSmsSuccess(@NotNull SmsResponse response) {
        boolean hasPhone = response.data.phoneNumberExit;

        if (hasPhone) {
            showToast(getString(R.string.please_login));
        } else {
            if (!adapter.contains(registerLoginFragment2)) {
                adapter.addAdapter(registerLoginFragment2);
                viewPager.setOffscreenPageLimit(2);
            }
            onPageSelected(1);
            registerLoginFragment2.startCountDown();
        }
        removeLoadingFragment();
    }

    @Override
    public void registerGetSmsFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }

    @Override
    public void registerSetPasswordSuccess(@NotNull BooleanResponse response) {
        if (response.data) {
            LoginResponse loginResponse = DBUtilsKt.getLoginResponse(this);
            if (loginResponse == null) {
                loginResponse = new LoginResponse();
                loginResponse.model = new LoginModel();
            }

            //判断是电话还是邮箱
            if (phoneOrAddress.contains("@")) {
                //邮箱
                if (loginResponse.model.email == null) {
                    loginResponse.model.email = new ArrayList();
                }
                loginResponse.model.email.add(phoneOrAddress);
            } else {
                //电话
                loginResponse.model.phone = phoneOrAddress;
            }
            loginResponse.model.loginCount = phoneOrAddress;

            loginResponse.model.userMessageLevel = "2";
            DBUtilsKt.saveLoginResponse(this, loginResponse);

            startActivity(new Intent(RegisterLoginActivity.this, HomeActivity.class));
            setResult(10086);
            finish();
        } else {
            showToast(getString(R.string.please_check_internet));
            removeLoadingFragment();
        }
    }

    @Override
    public void registerSetPasswordFail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }

    @Override
    public void registerVerificationSuccess(@NotNull RegisterVerificationResponse response) {
        LoginResponse loginResponse = DBUtilsKt.getLoginResponse(this);
        if (loginResponse == null || loginResponse.model == null) {
            loginResponse = new LoginResponse();
            loginResponse.model = new LoginModel();
        }
        loginResponse.model.token = response.data;
        loginResponse.model.userMessageLevel = "1";
        DBUtilsKt.saveLoginResponse(this, loginResponse);

        if (!adapter.contains(registerLoginFragment3)) {
            adapter.addAdapter(registerLoginFragment3);
            viewPager.setOffscreenPageLimit(3);
        }
        onPageSelected(2);
        viewPager.setOnTouchListener((v, event) -> true);

        removeLoadingFragment();
    }

    @Override
    public void registerVerificationFail(@NotNull Throwable throwable) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(data.getData());
        } else if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(uri);
        } else if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter.userIconUpdate(finalPic);
        } else if (requestCode == 10 && resultCode == 1001) {
            String name = data.getStringExtra(getString(R.string.DATA));
            registerLoginFragment4.setName(name);
        } else if (requestCode == 11 && resultCode == 1001) {
            companyNameBean = (ProjectIdNameBean) data.getSerializableExtra(getString(R.string.DATA));
            registerLoginFragment4.setCompany(companyNameBean);
        } else if (requestCode == 12 && resultCode == 1001) {
            String domain = data.getStringExtra(getString(R.string.DATA));
            registerLoginFragment4.setDomain(domain);
        } else if (requestCode == 13 && resultCode == 1001) {
            String position = data.getStringExtra(getString(R.string.DATA));
            registerLoginFragment4.setPosition(position);
        } else if (requestCode == 14 && resultCode == 1001) {
            String website = data.getStringExtra(getString(R.string.DATA));
            registerLoginFragment4.setWeb(website);
        } else if (requestCode == 15 && resultCode == 1001) {
            String email = data.getStringExtra(getString(R.string.DATA));
            registerLoginFragment4.setEmail(email);
        }
    }

    private void getPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10000);
    }

    private void getPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int version = Build.VERSION.SDK_INT;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = sdf.format(new Date());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (version < 24) {
                uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        startActivityForResult(intent, 10001);
    }


    private void startPhotoZoom(Uri uri) {
        finalPic = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (finalPic.exists()) {
                finalPic.delete();
            }
            finalPic.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cropImageUri = Uri.fromFile(finalPic);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        intent.putExtra("return-data", false);
        intent.putExtra("circleCrop", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);// no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    @Override
    public void userIconUpdateSuccess(@NotNull StringResponse response) {
        String data = response.getData();
        registerLoginFragment4.setIcon(data);
    }

    @Override
    public void userIconUpdateFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void postUserInfoType3Success(@NotNull LoginModel response) {
        removeLoadingFragment();
        LoginResponse loginResponse = DBUtilsKt.getLoginResponse(this);
        if (loginResponse == null) {
            loginResponse = new LoginResponse();
        }
        loginResponse.model = response;
        DBUtilsKt.saveLoginResponse(this, loginResponse);

        JPushInterface.setAlias(this, 1, response.uuid);

        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void postUserInfoType3Fail(@NotNull Throwable throwable) {
        removeLoadingFragment();
        showThrowable(throwable);
    }
}
