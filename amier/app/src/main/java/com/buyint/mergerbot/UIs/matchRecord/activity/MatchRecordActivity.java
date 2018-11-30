package com.buyint.mergerbot.UIs.matchRecord.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.contact.ContactAddFirstActivity;
import com.buyint.mergerbot.UIs.main.adapter.BttomGridAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.MatchRecordDetailClientAdapter;
import com.buyint.mergerbot.UIs.matchRecord.adapter.ContactListAdapter;
import com.buyint.mergerbot.UIs.matchRecord.adapter.MatchRecordAdapter;
import com.buyint.mergerbot.Utility.AnimationUtils;
import com.buyint.mergerbot.Utility.JsonParser;
import com.buyint.mergerbot.Utility.MyImagineUtilsKt;
import com.buyint.mergerbot.Utility.PermissionUtils;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.AppApplication;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.CheckStringBean;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchRecordResultBean;
import com.buyint.mergerbot.enums.SearchRelationType;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.DeleteEditText;
import com.buyint.mergerbot.view.OnBottomCallBack;
import com.buyint.mergerbot.view.OnTopCallBack;
import com.buyint.mergerbot.view.SmartRefreshLayoutHHMMade;
import com.buyint.mergerbot.view.VoiceLineView;
import com.buyint.mergerbot.view.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.sunflower.FlowerCollector;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.shape.RectLightShape;

/**
 * Created by huheming on 2018/10/11
 */

public class MatchRecordActivity extends BaseActivity {

    private VoiceLineView vl;
    private DeleteEditText etInput;
    private ImageView ivSay, ivNine;
    private GridView gvBottom;
    private RecyclerView recyclerView2, recyclerView1;
    private TextView tv2, title, tvGo, tvSend, tv1;
    private FloatingActionsMenu fam;
    private RelativeLayout rlFabMengCeng1, rlFabMengCeng2, rlFabMengCeng3, rlMengceng;
    //    private FloatingActionButton fab1, fab2;
    private SmartRefreshLayoutHHMMade srl1, srl2;
    private ExpandableListView elv;
    private DrawerLayout dl;
    private ImageView ivSelect;

    private int nowPage = 1;

    private SpeechRecognizer mIat;
    private boolean mTranslateEnable = false;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private SoundPool soundPool;
    private int load, ret;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private boolean userCannel = false, gogogo = true;
    private String printText;
    private int SPEAK_PERMISSION = 1000;
    private String type = SearchRelationType.ALL.name();
    private MatchRecordAdapter truePeopleAdapter;
    private MatchRecordAdapter falsePeopleAdapter;
    private ContactListAdapter contactAdapter;
    private File scanCardFile;
    private Uri scanCardUri;
    private Long[] mHits;
    private HighLight highLight;
    private MatchRecordDetailClientAdapter clientAdapter;
    private int clickC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitleWhiteAndTextBlank();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_record);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initIjkfly();

        dl = findViewById(R.id.rootLayout);
        tv1 = findViewById(R.id.activity_match_record_tv1);
        tv2 = findViewById(R.id.activity_match_record_tv2);
        recyclerView1 = findViewById(R.id.activity_match_record_recycler1);
        recyclerView2 = findViewById(R.id.activity_match_record_recycler2);
        srl1 = findViewById(R.id.activity_match_record_srl1);
        srl2 = findViewById(R.id.activity_match_record_srl2);
        ivSay = findViewById(R.id.activity_match_record_speak);
        etInput = findViewById(R.id.activity_match_record_et);
        tvSend = findViewById(R.id.activity_match_record_tv_send);
        ivNine = findViewById(R.id.activity_match_record_iv_nine);
        gvBottom = findViewById(R.id.activity_match_record_bottom_grid);
        rlFabMengCeng1 = findViewById(R.id.activity_match_record_fab_rl1);
        rlFabMengCeng2 = findViewById(R.id.activity_match_record_fab_rl2);
        rlFabMengCeng3 = findViewById(R.id.activity_match_record_fab_rl3);
        fam = findViewById(R.id.activity_match_record_fam);
//        fab1 = findViewById(R.id.activity_match_record_fab1);
//        fab2 = findViewById(R.id.activity_match_record_fab2);
        rlMengceng = findViewById(R.id.activity_match_record_mengceng2_center_view);
        vl = findViewById(R.id.activity_match_record_voiceline);
        tvGo = findViewById(R.id.activity_match_record2_tv_go);
        elv = findViewById(R.id.activity_match_record2_elv);
        ivSelect = findViewById(R.id.activity_match_record_select_iv);
        TextView tvReset = findViewById(R.id.activity_match_record_reset);
        TextView tvSure = findViewById(R.id.activity_match_record_sure);

        ivSelect.setOnClickListener(v -> dl.openDrawer(Gravity.END));

        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        dl.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        ArrayList<String> mainList = getMainList();
        ArrayList<ArrayList<CheckStringBean>> childList = getChildList();
        MatchRecordExpandableAdapter matchRecordExpandableAdapter = new MatchRecordExpandableAdapter(this, mainList, childList);
        elv.setAdapter(matchRecordExpandableAdapter);
        for (int i = 0; i < mainList.size(); i++) {
            elv.expandGroup(i);
        }
        elv.setCacheColorHint(0);
        elv.setGroupIndicator(null);
        elv.setDivider(null);

        tvSure.setOnClickListener(v -> {
            dl.closeDrawer(Gravity.END);
            recyclerView1.setVisibility(View.VISIBLE);
            srl1.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.GONE);
            srl2.setVisibility(View.GONE);
            tv1.setTextColor(ContextCompat.getColor(this, R.color.white));
            tv1.setBackground(ContextCompat.getDrawable(this, R.drawable.item_left_app_color));
            tv2.setTextColor(clickC);
            tv2.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            nowPage = 1;
            tvGo.setText("新增商友20人，客户5人");
            ArrayList<ArrayList<CheckStringBean>> childLists = matchRecordExpandableAdapter.getChildList();
            for (int i = 0; i < childLists.size(); i++) {
                for (int j = 0; j < childLists.get(i).size(); j++) {
                    CheckStringBean bean = childLists.get(i).get(j);
                    if (bean.isCheck) {
                        if (j == 0) {
                            type = SearchRelationType.ALL.name();
                            getMatchRecordList();
                        } else if (j == 1) {
                            type = SearchRelationType.WORK_MATE.name();
                            getMatchRecordList();
                        } else if (j == 2) {
                            type = SearchRelationType.WORK_MATE_AND_BUSINESS_FRIEND.name();
                            getMatchRecordList();
                        } else if (j == 3) {
                            type = SearchRelationType.BUSINESS_FRIEND.name();
                            getMatchRecordList();
                        } else if (j == 4) {
                            LoginResponse response = DBUtilsKt.getLoginResponse(this);
                            HttpHelper.matchRecordDetailClient(response.model.matchRecordPersonId).subscribe(matchRecordDetailClientResponse -> {
                                if (matchRecordDetailClientResponse.getData() != null && matchRecordDetailClientResponse.getData().size() > 0) {
                                    if (clientAdapter == null) {
                                        clientAdapter = new MatchRecordDetailClientAdapter(this, matchRecordDetailClientResponse.getData());
                                        recyclerView1.setAdapter(clientAdapter);
                                    } else {
                                        clientAdapter.setData(matchRecordDetailClientResponse.getData());
                                        recyclerView1.setAdapter(clientAdapter);
                                    }
                                } else {
                                    recyclerView1.setAdapter(new MatchRecordDetailClientAdapter(this, new ArrayList<>()));
                                }
                            }, this::showThrowable);
                        }
                    }
                }
            }
        });
        tvReset.setOnClickListener(v -> {
            ArrayList<String> mainLists = getMainList();
            ArrayList<ArrayList<CheckStringBean>> childLists = getChildList();
            matchRecordExpandableAdapter.reSetList(elv, mainLists, childLists);
        });

        attachKeyboardListeners();

//        rlFabMengCeng1.setOnClickListener(v -> fam.collapse());
//        rlFabMengCeng2.setOnClickListener(v -> fam.collapse());
//        rlFabMengCeng3.setOnClickListener(v -> fam.collapse());

        clickC = tv2.getCurrentTextColor();

        tv1.setOnClickListener(v -> {
            recyclerView1.setVisibility(View.VISIBLE);
            srl1.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.GONE);
            srl2.setVisibility(View.GONE);
            tv1.setTextColor(ContextCompat.getColor(this, R.color.white));
            tv1.setBackground(ContextCompat.getDrawable(this, R.drawable.item_left_app_color));
            tv2.setTextColor(clickC);
            tv2.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            nowPage = 1;
            tvGo.setText("新增商友20人，客户5人");
        });
        tv2.setOnClickListener(v -> {
            recyclerView1.setVisibility(View.GONE);
            srl1.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);
            srl2.setVisibility(View.VISIBLE);
            tv1.setTextColor(clickC);
            tv1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            tv2.setTextColor(ContextCompat.getColor(this, R.color.white));
            tv2.setBackground(ContextCompat.getDrawable(this, R.drawable.item_right_app_color));
            nowPage = 2;
            tvGo.setText("以下联系人还没有建立商业关系，添加事件可以扩大您的商业网络哟~");
        });

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        title = findViewById(R.id.toolbar_white_title);
        title.setText(getString(R.string.match_record));
        findViewById(R.id.toolbar_white_back).setOnClickListener(v -> onBackPressed());

        Drawable drawable = getResources().getDrawable(R.mipmap.match_record_title_pic);
        drawable.setBounds(0, 0, dp2px(80f), dp2px(16f));
        title.setCompoundDrawables(null, null, drawable, null);

        title.setOnClickListener(v -> ViewHelper.showOneLineCard(this, getString(R.string.are_you_sure_to_unbind_match_record), getString(R.string.alright), getString(R.string.cancel), (dialog, which) -> {
            HttpHelper.deleteNowOrTomorrow().subscribe(booleanResponse -> {
                if (booleanResponse.data) {
                    LoginResponse loginResponse = DBUtilsKt.getLoginResponse(MatchRecordActivity.this);
                    loginResponse.model.matchRecordPersonId = "";
                    DBUtilsKt.saveLoginResponse(MatchRecordActivity.this, loginResponse);
                    finish();
                }
            }, throwable -> showThrowable(throwable));
        }, (dialog, which) -> {
        }));

        findViewById(R.id.toolbar_white_right_add_rl).setOnClickListener(v -> ViewHelper.showMatchRecordPopupWindow(this, findViewById(R.id.toolbar_white_right_add), string -> {
            if (string.equals(getString(R.string.TYPE_0))) {
                //添加好友
                Intent intent = new Intent();
                intent.setClass(this, ContactAddFirstActivity.class);
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_0));
                startActivity(intent);
            } else if (string.equals(getString(R.string.TYPE_1))) {
                //上传通讯录
                Intent intent = new Intent();
                intent.setClass(this, UploadContactActivity.class);
                startActivity(intent);
            } else if (string.equals(getString(R.string.TYPE_2))) {
                //扫描名片
                //检查应用必备权限
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                takeCardPhoto();
                            } else {
                                showToast(getString(R.string.permission_denied));
                            }
                        });
            } else if (string.equals(getString(R.string.TYPE_3))) {
                //检查应用必备权限
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                Intent intent = new Intent();
                                intent.setClass(this, CaptureActivity.class);
                                startActivityForResult(intent, 123);
                            } else {
                                showToast(getString(R.string.permission_denied));
                            }
                        });
            } else if (string.equals(getString(R.string.TYPE_4))) {
                //我的二维码
                Intent intent = new Intent();
                intent.setClass(this, MyQRcodeActivity.class);
                startActivity(intent);
            }
        }));
        findViewById(R.id.toolbar_white_right_search_rl).setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchPeopleActivity.class);
            intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1));
            startActivity(intent);
        });

        rlMengceng.setAlpha(0);
        vl.setVisibility(View.GONE);
        rlMengceng.setVisibility(View.GONE);
        rlMengceng.setOnClickListener(v -> {
            userCannel = true;
            rlMengceng.setAlpha(0);
            vl.setVisibility(View.GONE);
            rlMengceng.setVisibility(View.GONE);
        });

        BttomGridAdapter bttomGridAdapter = new BttomGridAdapter(this, false);
        bttomGridAdapter.setGotoListener(type -> {

        });
        gvBottom.setAdapter(bttomGridAdapter);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivSay.setVisibility(View.GONE);
                } else {
                    ivSay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etInput.setOnClickListener(v -> showOrHideGvBottom(View.GONE));

        ivNine.setOnClickListener(v -> {
            if (gvBottom.getVisibility() == View.VISIBLE) {
                showOrHideGvBottom(View.GONE);
            } else {
                hintkeybord(etInput);
                new Handler().postDelayed(() -> showOrHideGvBottom(View.VISIBLE), 150);
            }
        });

        tvSend.setOnClickListener(v -> {
            if (etInput.getText().toString().trim().length() > 0) {

                printText = etInput.getText().toString();
                etInput.setText("");
                showOrHideGvBottom(View.GONE);

                searchPeople();
            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etInput.getText().toString().trim().length() > 0) {
                    tvSend.setVisibility(View.VISIBLE);
                    ivNine.setVisibility(View.GONE);
                } else {
                    tvSend.setVisibility(View.GONE);
                    ivNine.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivSay.setOnClickListener(v -> {

            //检查应用必备权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] strings = PermissionUtils.hasPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (strings.length > 0) {
                    ActivityCompat.requestPermissions(MatchRecordActivity.this, strings, SPEAK_PERMISSION);
                } else {
                    ivSay();
                }
            } else {
                ivSay();
            }
        });

        fam.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Intent intent = new Intent(MatchRecordActivity.this, MatchRecordDeepSearchActivity.class);
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1));
                startActivity(intent);
                fam.collapse();
//                rlFabMengCeng1.setVisibility(View.VISIBLE);
//                rlFabMengCeng2.setVisibility(View.VISIBLE);
//                rlFabMengCeng3.setVisibility(View.VISIBLE);
                fam.setButtonIcon(R.mipmap.match_record_fam_ic);
//                hideKeyBoard();
//                gvBottom.setVisibility(View.GONE);
            }

            @Override
            public void onMenuCollapsed() {
//                rlFabMengCeng1.setVisibility(View.GONE);
//                rlFabMengCeng2.setVisibility(View.GONE);
//                rlFabMengCeng3.setVisibility(View.GONE);
                fam.setButtonIcon(R.mipmap.match_record_fam_ic);
            }
        });


        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 100);
        load = soundPool.load(this, R.raw.icon_click, 1);

        showLoadingFragment(R.id.activity_match_record_fl);
        getContactList();
    }

    @NonNull
    private ArrayList<String> getMainList() {
        ArrayList<String> mainLists = new ArrayList<>();
        mainLists.add("显示关系人");
        return mainLists;
    }

    @NonNull
    private ArrayList<ArrayList<CheckStringBean>> getChildList() {
        ArrayList<ArrayList<CheckStringBean>> childList = new ArrayList<>();
        ArrayList<CheckStringBean> oneList = new ArrayList<>();
        CheckStringBean c1 = new CheckStringBean();
        c1.isCheck = true;
        c1.text = getString(R.string.all);
        oneList.add(c1);
        CheckStringBean c2 = new CheckStringBean();
        c2.isCheck = false;
        c2.text = getString(R.string.work_mate);
        oneList.add(c2);
        CheckStringBean c5 = new CheckStringBean();
        c5.isCheck = false;
        c5.text = getString(R.string.work_mate_team);
        oneList.add(c5);
        CheckStringBean c3 = new CheckStringBean();
        c3.isCheck = false;
        c3.text = getString(R.string.business_friend);
        oneList.add(c3);
        CheckStringBean c4 = new CheckStringBean();
        c4.isCheck = false;
        c4.text = getString(R.string.client);
        oneList.add(c4);
        childList.add(oneList);
        return childList;
    }

    private void getMatchRecordList() {
        HttpHelper.matchRecordList(type).subscribe(response -> {
            removeLoadingFragment();

            if (response != null && response.getData() != null && response.getData().size() > 0) {
                if (type.equals(SearchRelationType.ALL.name())) {
                    if (truePeopleAdapter == null) {
                        truePeopleAdapter = new MatchRecordAdapter(this, true, response.getData());
                        recyclerView1.setAdapter(truePeopleAdapter);
                    } else {
                        truePeopleAdapter.setData(response.getData());
                        recyclerView1.setAdapter(truePeopleAdapter);
                    }
                } else {
                    if (falsePeopleAdapter == null) {
                        falsePeopleAdapter = new MatchRecordAdapter(this, false, response.getData());
                        recyclerView1.setAdapter(falsePeopleAdapter);
                    } else {
                        falsePeopleAdapter.setData(response.getData());
                        recyclerView1.setAdapter(falsePeopleAdapter);
                    }
                }
            } else {
                recyclerView1.setAdapter(new MatchRecordAdapter(this, true, new ArrayList<>()));
            }
            checkFirst();
        }, throwable -> {
            removeLoadingFragment();
            showThrowable(throwable);
            checkFirst();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMatchRecordList();
    }

    public void checkFirst() {
        boolean firstMain = PreferencesKeeper.getFalseBoolean(this, getString(R.string.FIRST_MATCH_RECORD));
        if (!firstMain) {
            showHighLight();
            PreferencesKeeper.putBoolean(this, getString(R.string.FIRST_MATCH_RECORD), true);
        }
    }

    public void showHighLight() {
        highLight = new HighLight(this)
                .autoRemove(false)
                .intercept(true)
                .enableNext()
                .setClickCallback(() -> clickNow()).setOnNextCallback((hightLightView, targetView, tipView) -> {
                }).addHighLight(R.id.activity_match_record_bottom_rl, R.layout.activity_match_record_high_light_1, new OnBottomCallBack(this, 0), new RectLightShape())
                .addHighLight(title, R.layout.activity_match_record_high_light_2, new OnTopCallBack(this, 50), new RectLightShape());
        highLight.show();
    }

    public void clickNow() {
        if (highLight.isShowing() && highLight.isNext()) {
            highLight.next();
        } else {
            highLight.remove();
        }
    }

    public void getContactList() {
        HttpHelper.getContactsList().subscribe(list -> {
            if (list != null && list.size() > 0) {
                if (contactAdapter == null) {
                    contactAdapter = new ContactListAdapter(this, list, () -> {
                        startActivity(new Intent(MatchRecordActivity.this, UploadContactActivity.class));
                    });
                    recyclerView2.setAdapter(contactAdapter);
                } else {
                    contactAdapter.setData(list);
                    recyclerView2.setAdapter(contactAdapter);
                }
            }
        }, throwable -> showThrowable(throwable));
    }

    public void initIjkfly() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            showToast("语音初始化失败");
        }
    };

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        }

        @Override
        public void onError(SpeechError error) {
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            runOnUiThread(() -> removeMengCeng());
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showToast(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showToast(getString(R.string.you_may_not_speak));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if (!mTranslateEnable) {
                printResult(results);
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            vl.setVolume(volume * 2);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d("hhm", "session id =" + sid);
            //	}
        }
    };

    //说话处理逻辑
    private void printResult(RecognizerResult results) {

        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuilder resultBuffer = new StringBuilder();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        if (gogogo) {

            gogogo = false;
            new Handler().postDelayed(() -> {

                if (userCannel) {
                    userCannel = false;
                    return;
                }

                printText = resultBuffer.toString();

                if (TextUtils.isEmpty(printText)) {

                    showToast(getString(R.string.you_may_not_speak));

                    gogogo = true;
                    removeMengCeng();

                    return;
                }

                removeMengCeng();
                searchPeople();

            }, 1000);
        }

    }

    private void searchPeople() {
        showLoadingFragment(R.id.activity_match_record_fl);
        HttpHelper.searchPeople2(printText).subscribe(list -> {
            removeLoadingFragment();

            hideKeyBoard();

            Intent intent = new Intent(this, MatchRecordResultActivity.class);
            MatchRecordResultBean bean = new MatchRecordResultBean();
            bean.list = list;
            intent.putExtra(getString(R.string.DATA), bean);
            intent.putExtra(getString(R.string.NAME), printText);
            startActivity(intent);

        }, throwable -> {
            showThrowable(throwable);
            removeLoadingFragment();
        });
    }

    public void setUserSpeakParams() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = PreferencesKeeper.getFalseBoolean(this, getString(R.string.pref_key_translate));
//        this.mTranslateEnable = mSharedPreferences.getBoolean(this.getString(R.string.pref_key_translate), false);
        if (mTranslateEnable) {
            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
        }

        if (AppApplication.language.equals(getString(R.string.ENGLISH))) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
            }
        }
        //此处用于设置dialog中不显示错误码信息
        mIat.setParameter("view_tips_plain", "false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String iat_vadbos_preference = PreferencesKeeper.getString(this, "iat_vadbos_preference");
        if (TextUtils.isEmpty(iat_vadbos_preference)) {
            iat_vadbos_preference = "10000";
        }
        mIat.setParameter(SpeechConstant.VAD_BOS, iat_vadbos_preference);

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String iat_vadeos_preference = PreferencesKeeper.getString(this, "iat_vadeos_preference");
        if (TextUtils.isEmpty(iat_vadbos_preference)) {
            iat_vadeos_preference = "2500";
        }
        mIat.setParameter(SpeechConstant.VAD_EOS, iat_vadeos_preference);

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        String iat_punc_preference = PreferencesKeeper.getString(this, "iat_punc_preference");
        if (TextUtils.isEmpty(iat_vadbos_preference)) {
            iat_punc_preference = "1";
        }
        mIat.setParameter(SpeechConstant.ASR_PTT, iat_punc_preference);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");

    }

    public void ivSay() {
        gogogo = true;

        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (input != null && input.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        if (gvBottom.getVisibility() == View.VISIBLE) {
            showOrHideGvBottom(View.GONE);
        }

        soundPool.play(load, 1, 1, 0, 0, 1);

        rlMengceng.setVisibility(View.VISIBLE);
        vl.setVisibility(View.VISIBLE);
        rlMengceng.setAlpha(0);
        rlMengceng.animate().alpha(0).alphaBy(1).setDuration(600).start();

        new Handler().postDelayed(() -> {

            userCannel = false;

            // 移动数据分析，收集开始听写事件
            FlowerCollector.onEvent(MatchRecordActivity.this, "iat_recognize");

            mIatResults.clear();
            // 设置参数
            setUserSpeakParams();

            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showToast("听写失败");
            }
        }, 400);
    }

    private void showOrHideGvBottom(int visiable) {
        gvBottom.setVisibility(visiable);
    }

    public void removeMengCeng() {
        AnimationUtils.alpha10Animate(rlMengceng, 1000, () -> {
            rlMengceng.setAlpha(0);
            rlMengceng.setVisibility(View.GONE);
            vl.setVisibility(View.GONE);
        });
    }

    protected void onShowKeyboard(int keyboardHeight) {
        showOrHideGvBottom(View.GONE);
    }

    protected void onHideKeyboard() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
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
        } else if (requestCode == 123) {
            if (null != data && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                if (extras.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = extras.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(this, ScanQRcodeActivity.class);
                    intent.putExtra(getString(R.string.DATA), result);
                    startActivity(intent);
                } else {
                    //解析失败
                    showToast(getString(R.string.analyse_qr_code_fail));
                }
            }
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
