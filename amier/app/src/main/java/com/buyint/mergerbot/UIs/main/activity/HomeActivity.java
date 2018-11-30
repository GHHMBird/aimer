package com.buyint.mergerbot.UIs.main.activity;

import android.Manifest;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.WebActivity;
import com.buyint.mergerbot.UIs.contact.ContactAddFirstActivity;
import com.buyint.mergerbot.UIs.main.adapter.QM3QABean;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QM3QAAdapter;
import com.buyint.mergerbot.UIs.matchRecord.activity.MatchRecordActivity;
import com.buyint.mergerbot.UIs.matchRecord.activity.MatchRecordSplashActivity;
import com.buyint.mergerbot.UIs.matchRecord.activity.MyQRcodeActivity;
import com.buyint.mergerbot.UIs.matchRecord.activity.ScanQRcodeActivity;
import com.buyint.mergerbot.UIs.matchRecord.activity.UploadContactActivity;
import com.buyint.mergerbot.UIs.trainAimer.activity.TrainAimerActivity;
import com.buyint.mergerbot.UIs.user.activity.MyActivity;
import com.buyint.mergerbot.UIs.user.activity.MyProjectActivity;
import com.buyint.mergerbot.UIs.user.activity.SettingActivity;
import com.buyint.mergerbot.Utility.MyImagineUtilsKt;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.bus.annotation.BusReceiver;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.GetProductRequest;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchCompanyResponse;
import com.buyint.mergerbot.enums.ShowNameType;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.OnCenterCallBack;
import com.buyint.mergerbot.view.SmartRefreshLayoutHHMMade;
import com.buyint.mergerbot.view.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.sunflower.FlowerCollector;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.shape.CircleLightShape;

/**
 * Created by huheming on 2018/10/10
 */
public class HomeActivity extends BaseActivity {

    private long exitTime = 0;
    private boolean isDialogShow = false;
    private RecyclerView recyclerView;
    private QM3QAAdapter qm3QAAdapter;
    private SmartRefreshLayoutHHMMade swipe;
    private RelativeLayout rlMore, rlAdd;
    private int nextPage;//默认为0   推荐项目     2智搜项目     1是跳转qa对话
    private long dbCount;
    private ArrayList<QM3QABean> dbList = new ArrayList<>();
    private LoginResponse loginResponse;
    private ImageView menuIcon;
    private TextView menuName;
    private File scanCardFile = null;
    private Uri scanCardUri = null;
    private HighLight highLight = null;
    private DrawerLayout dl;
    private View leftView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initView();
    }

    public void initView() {
        if (loginResponse == null) {
            loginResponse = DBUtilsKt.getLoginResponse(this);
        }
        dbCount = DBUtilsKt.dbGetLong(this, loginResponse.model.uuid + getString(R.string.dbCount));

        rlMore = findViewById(R.id.toolbar_image_more);
        rlAdd = findViewById(R.id.toolbar_image_add);
        rlAdd.setOnClickListener(v -> {
            loginResponse = DBUtilsKt.getLoginResponse(HomeActivity.this);
            ViewHelper.showMatchRecordPopupWindow(HomeActivity.this, rlAdd, string -> {
                if (string.equals(getString(R.string.TYPE_0))) {
                    //添加好友
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                        intent.setClass(HomeActivity.this, ContactAddFirstActivity.class);
                        intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_0));
                    } else {
                        intent.setClass(HomeActivity.this, MatchRecordSplashActivity.class);
                    }
                    startActivity(intent);
                } else if (string.equals(getString(R.string.TYPE_1))) {
                    //上传通讯录
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                        intent.setClass(HomeActivity.this, UploadContactActivity.class);
                    } else {
                        intent.setClass(HomeActivity.this, MatchRecordSplashActivity.class);
                    }
                    startActivity(intent);
                } else if (string.equals(getString(R.string.TYPE_2))) {
                    //扫描名片
                    if (!TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                        //检查应用必备权限
                        RxPermissions rxPermissions = new RxPermissions(HomeActivity.this);
                        rxPermissions.request(Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(aBoolean -> {
                                    if (aBoolean) {
                                        takeCardPhoto();
                                    } else {
                                        showToast(getString(R.string.permission_denied));
                                    }
                                });
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, MatchRecordSplashActivity.class);
                        startActivity(intent);
                    }
                } else if (string.equals(getString(R.string.TYPE_3))) {
                    //检查应用必备权限
                    RxPermissions rxPermissions = new RxPermissions(HomeActivity.this);
                    rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    Intent intent = new Intent();
                                    if (!TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                                        intent.setClass(HomeActivity.this, CaptureActivity.class);
                                        startActivityForResult(intent, 123);
                                    } else {
                                        intent.setClass(HomeActivity.this, MatchRecordSplashActivity.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    showToast(getString(R.string.permission_denied));
                                }
                            });
                } else if (string.equals(getString(R.string.TYPE_4))) {
                    //我的二维码
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                        intent.setClass(HomeActivity.this, MyQRcodeActivity.class);
                    } else {
                        intent.setClass(HomeActivity.this, MatchRecordSplashActivity.class);
                    }
                    startActivity(intent);
                }
            });
        });

//        LinearLayout llBottom1 = findViewById(R.id.activity_home_bottom1_ll);
        LinearLayout llBottom2 = findViewById(R.id.activity_home_bottom2_ll);
        LinearLayout llBottom3 = findViewById(R.id.activity_home_bottom3_ll);

        llBottom2.setOnClickListener(v -> {
            loginResponse = DBUtilsKt.getLoginResponse(HomeActivity.this);
            if (TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                startActivity(new Intent(HomeActivity.this, MatchRecordSplashActivity.class));
            } else {
                startActivity(new Intent(HomeActivity.this, MatchRecordActivity.class));
            }
        });
        llBottom3.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, WebActivity.class);
            intent.putExtra(getString(R.string.TITLE), getString(R.string.human_relation));
            intent.putExtra("params", "http://117.184.66.210:8091/relation/#/");
            startActivity(intent);
        });

//        RelativeLayout rlFam = findViewById(R.id.activity_home_rl);
//        rlFam.setOnClickListener(v -> fam.collapse());

        FloatingActionsMenu fam = findViewById(R.id.activity_home_fam);
        fam.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fam.setButtonIcon(R.mipmap.match_record_pei);
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                fam.collapse();
            }

            @Override
            public void onMenuCollapsed() {
                fam.setButtonIcon(R.mipmap.match_record_pei);
            }
        });

        swipe = findViewById(R.id.activity_main_swipe);
        recyclerView = findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        qm3QAAdapter = new QM3QAAdapter(this);
        qm3QAAdapter.setMainListener(() -> {
            nextPage = 2;
            rlMore.setVisibility(View.GONE);
            openIntellectSearch();
        });
        recyclerView.setAdapter(qm3QAAdapter);

        swipe.setOnRefreshListener(refreshLayout -> {
            if (nextPage == 0) {
                getProduct();
            } else if (nextPage == 1) {
                swipe.finishRefresh();
                swipe.finishLoadMore();
                getCacheData();
            } else if (nextPage == 2) {
                swipe.finishRefresh();
                swipe.finishLoadMore();
            }
        });

        dl = findViewById(R.id.activity_home_dl);
        leftView = findViewById(R.id.activity_main_left);
        menuName = findViewById(R.id.menu_layout_name);
        menuIcon = findViewById(R.id.menu_layout_icon);
        leftView.setOnClickListener(v -> {
        });

        if (loginResponse.model.userPrivacySetting == null) {
            menuName.setText(TextUtils.isEmpty(loginResponse.model.englishName) ? getString(R.string.unknown_user) : loginResponse.model.englishName);
        } else {
            if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.REALNAME.name())) {
                menuName.setText(TextUtils.isEmpty(loginResponse.model.userName) ? getString(R.string.unknown_user) : loginResponse.model.userName);
            } else if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.NICKNAME.name())) {
                menuName.setText(TextUtils.isEmpty(loginResponse.model.englishName) ? getString(R.string.unknown_user) : loginResponse.model.englishName);
            } else if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.NAMDE.name())) {
                menuName.setText(TextUtils.isEmpty(loginResponse.model.userName) ? getString(R.string.unknown_user) : getString(R.string.sir_or_miss_2_start) + loginResponse.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end));
            }
        }

        Glide.with(this).load(loginResponse.model.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(new BitmapImageViewTarget(menuIcon) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                menuIcon.setImageDrawable(drawable);
            }
        });
        findViewById(R.id.my_project).setOnClickListener(v -> {
            startActivityForResult(new Intent(HomeActivity.this, MyProjectActivity.class), 99);
            dl.closeDrawer(leftView);
        });

        findViewById(R.id.menu_layout_match_record).setOnClickListener(v -> {
            loginResponse = DBUtilsKt.getLoginResponse(HomeActivity.this);
            if (TextUtils.isEmpty(loginResponse.model.matchRecordPersonId)) {
                startActivity(new Intent(HomeActivity.this, MatchRecordSplashActivity.class));
            } else {
                startActivity(new Intent(HomeActivity.this, MatchRecordActivity.class));
            }
            dl.closeDrawer(leftView);
        });

        findViewById(R.id.menu_layout_setting).setOnClickListener(v -> {
            startActivityForResult(new Intent(HomeActivity.this, SettingActivity.class), 99);
            dl.closeDrawer(leftView);
        });

        findViewById(R.id.menu_layout_my_account).setOnClickListener(v -> {
            if (getString(R.string.SECRETARY).equals(loginResponse.model.type)) {

            } else {
                startActivityForResult(new Intent(this, MyActivity.class), 99);
                dl.closeDrawer(leftView);
            }
        });

        findViewById(R.id.menu_layout_training_aimer).setOnClickListener(v -> {
            startActivity(new Intent(this, TrainAimerActivity.class));
            dl.closeDrawer(leftView);
        });

        findViewById(R.id.menu_layout_icon).setOnClickListener(v -> {
            if (getString(R.string.SECRETARY).equals(loginResponse.model.type)) {

            } else {
                startActivityForResult(new Intent(this, MyActivity.class), 99);
                dl.closeDrawer(leftView);
            }
        });
        findViewById(R.id.menu_layout_help).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, UserBackActivity.class));
            dl.closeDrawer(leftView);
        });

        rlMore.setOnClickListener(v -> dl.openDrawer(leftView));

        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (input != null && input.isActive() && getCurrentFocus() != null) {
                    if (getCurrentFocus().getWindowToken() != null) {
                        input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        showLoadingFragment(R.id.activity_main_fl);
        getProduct();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @BusReceiver
    public void StringEvent(String event) {
        if (event.equals(getString(R.string.buyint_text_size_change)) || event.equals(getString(R.string.buyint_language_change)) || event.equals(getString(R.string.buyint_color_change))) {
            recreate();
        }
        if (event.equals(getString(R.string.USER_ICON_CHANGE))) {
            loginResponse = DBUtilsKt.getLoginResponse(this);
            Glide.with(this).load(loginResponse.model.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(new BitmapImageViewTarget(menuIcon) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                    drawable.setCircular(true);
                    menuIcon.setImageDrawable(drawable);
                }
            });

            if (loginResponse.model.userPrivacySetting == null) {
                menuName.setText(TextUtils.isEmpty(loginResponse.model.englishName) ? getString(R.string.unknown_user) : loginResponse.model.englishName);
            } else {
                if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.REALNAME.name())) {
                    menuName.setText(TextUtils.isEmpty(loginResponse.model.userName) ? getString(R.string.unknown_user) : loginResponse.model.userName);
                } else if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.NICKNAME.name())) {
                    menuName.setText(TextUtils.isEmpty(loginResponse.model.englishName) ? getString(R.string.unknown_user) : loginResponse.model.englishName);
                } else if (loginResponse.model.userPrivacySetting.showNameType.equals(ShowNameType.NAMDE.name())) {
                    menuName.setText(TextUtils.isEmpty(loginResponse.model.userName) ? getString(R.string.unknown_user) : getString(R.string.sir_or_miss_2_start) + loginResponse.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end));
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String TYPE = intent.getStringExtra(getString(R.string.TYPE));
        if (getString(R.string.TYPE_0).equals(TYPE)) {//重新匹配的返回值
            MatchCompanyResponse response = (MatchCompanyResponse) intent.getSerializableExtra(getString(R.string.result));

            if (response == null || response.data == null || response.data.relationProject == null) {
                QM3QABean bean = new QM3QABean();
                bean.code = getString(R.string.RECEIVE);
                bean.name = getString(R.string.there_is_no_result_please_wait);
                qm3QAAdapter.addData(bean);
                insertDataBase(bean, false);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
            } else {

                QM3QABean bean = new QM3QABean();
                bean.code = getString(R.string.RECEIVE);
                bean.name = getString(R.string.this_is_project_rematch_for_you);
                qm3QAAdapter.addData(bean);
                insertDataBase(bean, false);

                QM3QABean cb2 = new QM3QABean();
                cb2.code = getString(R.string.ZPXM);
                cb2.name = getString(R.string.main_text);
                cb2.data = response.data;
                cb2.type = getString(R.string.QUICK);
                qm3QAAdapter.addData(cb2);
                insertDataBase(cb2, true);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
            }

        } else if (getString(R.string.TYPE_1).equals(TYPE)) {//深度匹配的返回值
            MatchCompanyResponse response = (MatchCompanyResponse) intent.getSerializableExtra(getString(R.string.result));

            QM3QABean bean = new QM3QABean();
            bean.code = getString(R.string.RECEIVE);
            bean.name = getString(R.string.this_is_project_rematch_for_you);
            qm3QAAdapter.addData(bean);
            insertDataBase(bean, false);

            QM3QABean cb2 = new QM3QABean();
            cb2.code = getString(R.string.ZPXM);
            cb2.name = getString(R.string.main_text);
            cb2.data = response.data;
            cb2.type = getString(R.string.QUICK);
            qm3QAAdapter.addData(cb2);
            insertDataBase(cb2, true);
            recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
        }
    }

    private void getProduct() {
        GetProductRequest request = new GetProductRequest();
        if (loginResponse == null) {
            loginResponse = DBUtilsKt.getLoginResponse(this);
        }
        request.userIntentions = loginResponse.model.userIntentions;
        request.industry_Classification = loginResponse.model.registerIndustryClassification;

        HttpHelper.getProduct(request).subscribe(response -> {

            if (response != null && response.data != null && response.data.relationProject != null && response.data.relationProject.size() > 0) {

                for (int i = 0; i < response.data.relationProject.size(); i++) {
                    response.data.relationProject.get(i).type = getString(R.string.userRecommend);
                }

                qm3QAAdapter.clearData();
                QM3QABean cb = new QM3QABean();
                cb.code = getString(R.string.RECEIVE);
                cb.name = getString(R.string.main_text);
                qm3QAAdapter.addData(cb);
                QM3QABean cb2 = new QM3QABean();
                cb2.code = getString(R.string.ZPXM);
                cb2.name = getString(R.string.main_text);
                cb2.data = response.data;
                cb2.type = getString(R.string.MAIN);
                qm3QAAdapter.addData(cb2);
            }

            swipe.finishRefresh();
            swipe.finishLoadMore();
            removeLoadingFragment();
            checkFirst();

        }, throwable -> {
            removeLoadingFragment();
            checkFirst();
            swipe.finishRefresh();
            swipe.finishLoadMore();
            showThrowable(throwable);
        });
    }

    @Override
    public void onBackPressed() {
        if (!isDialogShow) {
            exit();
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
            ViewHelper.showOneLineCard(this,
                    getString(R.string.sure_to_quit),
                    getString(R.string.alright),
                    getString(R.string.cancel),
                    (dialog, which) -> finish(),
                    (dialog, which) -> {
                        exitTime = 0;
                        isDialogShow = false;
                    });
        } else {
            exitTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onDestroy() {
        if (qm3QAAdapter != null) {
            qm3QAAdapter.destory();
            qm3QAAdapter = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart("hhm");
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd("hhm");
        FlowerCollector.onPause(this);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 100) {//退出登录
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        } else if (requestCode == 99 && resultCode == 102) {//开启快速匹配
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (requestCode == 1000 && resultCode == 1001) {
            finish();
        } else if (requestCode == 123) {
            if (null != data && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                if (extras.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = extras.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(HomeActivity.this, ScanQRcodeActivity.class);
                    intent.putExtra(getString(R.string.DATA), result);
                    startActivity(intent);
                } else {
                    //解析失败
                    showToast(getString(R.string.analyse_qr_code_fail));
                }
            }
        } else if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            showLoadingFragment(R.id.activity_main_fl);
            String path = MyImagineUtilsKt.getBtpathFormUri(this, scanCardUri);

            //进行裁剪
            RequestBody create = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
            MultipartBody.Part createFormData = MultipartBody.Part.createFormData("file", new File(path).getName(), create);

            HttpHelper.sendCard(createFormData).subscribe(b -> {
                if (b) {
                    showToast(getString(R.string.scan_business_card_success));
                }
                removeLoadingFragment();
            }, throwable -> {
                showThrowable(throwable);
                removeLoadingFragment();
            });
        }
    }

    public void insertDataBase(QM3QABean bean, boolean isClear) {
        bean.speakCount = dbCount;
        dbList.add(bean);
        updateDb(isClear);
    }

    public void getCacheData() {
        ArrayList<QM3QABean> list = qm3QAAdapter.getList();
        QM3QABean qm3QABean = list.get(0);
        long beanDBCount = qm3QABean.speakCount;
        if (beanDBCount > 1) {

            if (loginResponse == null) {
                loginResponse = DBUtilsKt.getLoginResponse(this);
            }
            QM3QABean[] beans = DBUtilsKt.getQM3QABeans(this, getString(R.string.buyint) + loginResponse.model.uuid + (beanDBCount - 1));
            if (beans != null) {
                ArrayList<QM3QABean> lists = new ArrayList<>();
                Collections.addAll(lists, beans);
                lists.addAll(list);
                qm3QAAdapter.addList(lists);
                recyclerView.scrollToPosition(beans.length);
            }
        }
    }

    public void updateDb(boolean isClear) {
        QM3QABean[] s = new QM3QABean[dbList.size()];
        for (int i = 0; i < dbList.size(); i++) {
            s[i] = dbList.get(i);
        }
        if (loginResponse == null) {
            loginResponse = DBUtilsKt.getLoginResponse(this);
        }
        DBUtilsKt.putQM3QABeans(this, getString(R.string.buyint) + loginResponse.model.uuid + dbCount, s);
        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

        if (isClear) {
            dbList.clear();
        }
    }

    //智配关系
    public void openIntellectSearch() {
        StatService.onEvent(this, getString(R.string.ZSXM), "从首页跳转智搜项目并匹配结果", 1);
        hideKeyBoard();
    }

    public void showHighLight() {
        highLight = new HighLight(this)
                .intercept(true)
                .enableNext()
                .setClickCallback(() -> {
                }).setOnLayoutCallback(() -> {
                    highLight.addHighLight(R.id.activity_home_fam, R.layout.activity_main_high_light, new OnCenterCallBack(this, 45), new CircleLightShape());
                    highLight.show();
                });
    }

    public void checkFirst() {
        boolean firstMain = PreferencesKeeper.getFalseBoolean(this, getString(R.string.FIRST_MAIN));
        if (!firstMain) {
            showHighLight();
            PreferencesKeeper.putBoolean(this, getString(R.string.FIRST_MAIN), true);
        }
    }

    private void takeCardPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int version = Build.VERSION.SDK_INT;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = sdf.format(new Date());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            scanCardFile = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (version < 24) {
                scanCardUri = Uri.fromFile(scanCardFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, scanCardUri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, scanCardFile.getAbsolutePath());
                scanCardUri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, scanCardUri);
            }
        }
        startActivityForResult(intent, 111);
    }
}
