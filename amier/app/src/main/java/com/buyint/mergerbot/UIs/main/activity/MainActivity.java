package com.buyint.mergerbot.UIs.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.JavaScriptinterface;
import com.buyint.mergerbot.UIs.WebActivity;
import com.buyint.mergerbot.UIs.main.adapter.BttomGridAdapter;
import com.buyint.mergerbot.UIs.main.adapter.QM3QABean;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QM3QAAdapter;
import com.buyint.mergerbot.UIs.match.activity.DeepMatchActivity;
import com.buyint.mergerbot.UIs.match.activity.QuickMatchAddActivity;
import com.buyint.mergerbot.UIs.match.fragment.QAMoneyFragment;
import com.buyint.mergerbot.UIs.matchRecord.activity.ScanQRcodeActivity;
import com.buyint.mergerbot.UIs.verification.activity.NotVerificationActivity;
import com.buyint.mergerbot.Utility.AnimationUtils;
import com.buyint.mergerbot.Utility.JsonParser;
import com.buyint.mergerbot.Utility.MyImagineUtilsKt;
import com.buyint.mergerbot.Utility.PermissionUtils;
import com.buyint.mergerbot.base.AppApplication;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchCompanyResponse;
import com.buyint.mergerbot.dto.MoneyFragmentDto;
import com.buyint.mergerbot.dto.QASpeakRequest;
import com.buyint.mergerbot.dto.QASpeakResponse;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.tts.PlayingListener;
import com.buyint.mergerbot.tts.Synthesizer;
import com.buyint.mergerbot.tts.Voice;
import com.buyint.mergerbot.view.OnCenterCallBack;
import com.buyint.mergerbot.view.SmartRefreshLayoutHHMMade;
import com.buyint.mergerbot.view.VoiceLineView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.microsoft.cognitiveservices.speechrecognition.DataRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.MicrophoneRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionMode;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.shape.CircleLightShape;

/**
 * Created by CXC on 2018/4/3
 */

public class MainActivity extends BaseActivity {

    private int SPEAK_PERMISSION = 100;
    private RecyclerView recyclerView;
    private QM3QAAdapter qm3QAAdapter;
    private SmartRefreshLayoutHHMMade swipe;
    private EditText etInput;
    private TextView tvSend;
    private ImageView ivNine;
    private GridView gvBottom;
    private RelativeLayout rlMengceng2, ivBack;
    private VoiceLineView vl;
    private String printText;
    private SpeechRecognizer mIat;
    private SpeechSynthesizer mTts;
    private String voicer;
    private int ret;
    private boolean mTranslateEnable = false;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private String projectId;
    private int nextPage;//     2智搜项目     1是跳转qa对话
    private TextView topText, tvMengCeng;
    private WebView webView;
    private ImageView ivQAUserBack;
    private ImageView ivSay;
    private View gvBottomMengCeng1;
    private View gvBottomMengCeng2;
    private String[] textTipQuick;
    private int nowTip = 0;
    private boolean userCannel = false;
    private String project_price = "";
    private String project_profit = "";
    private long dbCount;
    private ArrayList<QM3QABean> dbList = new ArrayList<>();
    private boolean shouldAddDBNumber = false;
    private LinkedList<String> urlList = new LinkedList<>();
    private boolean isRemoveLastUrl = false;
    private SoundPool soundPool;
    private int load;
    private boolean isShowMoneyFragment = false;
    private LoginResponse loginResponse;
    private View mengceng2View;
    private View mengceng2Text, mengceng2Text1, mengceng2Text2, mengceng2Text3, mengceng2Text4;
    private QASpeakResponse qaEndResponse;
    private boolean aimerShouldSay = true;
    private Uri scanCardUri = null;
    private boolean gogogo = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initView();
    }

    public void initView() {
        textTipQuick = getResources().getStringArray(R.array.match_text);

        if (AppApplication.language.equals(getString(R.string.CHINESE))) {
            voicer = getString(R.string.xiaoyan);
        } else {
            voicer = getString(R.string.kaiselin);
        }
        initIjkfly();

        if (loginResponse == null) {
            loginResponse = DBUtilsKt.getLoginResponse(this);
        }
        dbCount = DBUtilsKt.dbGetLong(this, loginResponse.model.uuid + getString(R.string.dbCount));

        attachKeyboardListeners();

        etInput = findViewById(R.id.activity_main_et);
        handler.sendEmptyMessage(0);

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
        ImageView tip = findViewById(R.id.activity_main_tip);
        tvSend = findViewById(R.id.activity_main_tv_send);
        ivNine = findViewById(R.id.activity_main_iv_nine);
        ivBack = findViewById(R.id.toolbar_image_back);
        topText = findViewById(R.id.activity_main_top_text);
        ivQAUserBack = findViewById(R.id.activity_main_qa_back_iv);
        ivQAUserBack.setOnClickListener(v -> {
            if (qaEndResponse != null) {
                Intent intent = new Intent(MainActivity.this, QASpeakBackActivity.class);
                intent.putExtra(getString(R.string.DATA), qaEndResponse);
                startActivity(intent);
            }
        });
        ivBack.setVisibility(View.GONE);
        ivBack.setOnClickListener(v -> {
            if (rlMengceng2.getAlpha() != 0) {
                return;
            }
            ivQAUserBack.setVisibility(View.GONE);
            if (nextPage == 1 || nextPage == 0) {
                onBackPressed();
            } else if (nextPage == 2) {
                if (urlList.size() > 0) {
                    if (isRemoveLastUrl) {
                        isRemoveLastUrl = false;
                        urlList.remove(urlList.size() - 1);
                        if (urlList.size() > 0) {
                            String url = urlList.get(urlList.size() - 1);
                            urlList.remove(urlList.size() - 1);
                            webView.loadUrl(url);
                        } else {
                            nextPage = 1;
                            webView.setVisibility(View.GONE);
                        }
                    } else {
                        String url = urlList.get(urlList.size() - 1);
                        urlList.remove(urlList.size() - 1);
                        webView.loadUrl(url);
                    }
                } else {
                    nextPage = 1;
                    webView.setVisibility(View.GONE);
                }
            }
        });
        tvMengCeng = findViewById(R.id.activity_main_mengceng2_tv);
        rlMengceng2 = findViewById(R.id.activity_main_mengceng2);
        mengceng2View = findViewById(R.id.activity_main_mengceng2_center_view);
        mengceng2Text = findViewById(R.id.activity_main_mengceng2_center_text);
        mengceng2Text1 = findViewById(R.id.activity_main_mengceng2_center_text_1);
        mengceng2Text2 = findViewById(R.id.activity_main_mengceng2_center_text_2);
        mengceng2Text3 = findViewById(R.id.activity_main_mengceng2_center_text_3);
        mengceng2Text4 = findViewById(R.id.activity_main_mengceng2_center_text_4);
        rlMengceng2.setOnClickListener(v -> {
            userCannel = true;
            rlMengceng2.setAlpha(0);
            rlMengceng2.setVisibility(View.GONE);
            tvMengCeng.setText("");
        });
        rlMengceng2.setAlpha(0);
        rlMengceng2.setVisibility(View.GONE);
        vl = findViewById(R.id.activity_main_voiceline);
        gvBottom = findViewById(R.id.activity_main_bottom_grid);
        gvBottomMengCeng1 = findViewById(R.id.activity_main_gv_bottom_mengceng1);
        gvBottomMengCeng2 = findViewById(R.id.activity_main_gv_bottom_mengceng2);
        gvBottomMengCeng1.setOnClickListener(v -> showOrHideGvBottom(View.GONE));
        gvBottomMengCeng2.setOnClickListener(v -> showOrHideGvBottom(View.GONE));
        BttomGridAdapter bttomGridAdapter = new BttomGridAdapter(this, false);
        bttomGridAdapter.setGotoListener(type -> {
            showOrHideGvBottom(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
            topText.setText(getString(R.string.secret_match_project));
            webView.setVisibility(View.GONE);
            qm3QAAdapter.clearData();
            if (type.equals(getString(R.string.quick_match))) {
                QM3QABean b = createTimeBean();

                shouldAddDBNumber = false;
                dbCount++;
                DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                insertDataBase(b, false);
                qm3QAAdapter.addData(b);
                qaStart(true);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                projectId = "";
                this.nextPage = 1;
                mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                setMengCengVisible(View.GONE);
            } else if (type.equals(getString(R.string.intelligence_search))) {
                QM3QABean b = createTimeBean();
                qm3QAAdapter.addData(b);
                QM3QABean bean = new QM3QABean();
                bean.code = getString(R.string.RECEIVE);
                bean.name = getString(R.string.what_project_do_you_need);
                speak(getString(R.string.what_project_do_you_need));
                qm3QAAdapter.addData(bean);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                nextPage = 2;
            }
        });
        gvBottom.setAdapter(bttomGridAdapter);
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

                aimerShouldSay = false;

                ivQAUserBack.setVisibility(View.GONE);
                printText = etInput.getText().toString();
                etInput.setText("");
                showOrHideGvBottom(View.GONE);

                if (this.nextPage == 0) {

                    if (printText.contains(getString(R.string.quick_match))) {
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(true);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        this.nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                    } else if (printText.contains(getString(R.string.deep_match))) {
                        openDeepMatchActivity();
                    } else if (printText.contains(getString(R.string.wisdom_in_opportunity))) {
                        openWebActivity();
                    } else {

                        showOrHideGvBottom(View.GONE);
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(MainActivity.this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                        QM3QABean bean = new QM3QABean();
                        bean.code = getString(R.string.SEND);
                        bean.name = printText;
                        qm3QAAdapter.addData(bean);
                        insertDataBase(bean, false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                        qaSpeak();

                    }

                } else if (this.nextPage == 1) {

                    if (shouldAddDBNumber) {
                        shouldAddDBNumber = false;
                        dbCount++;
                    }
                    QM3QABean bean = new QM3QABean();
                    bean.code = getString(R.string.SEND);
                    bean.name = printText;
                    qm3QAAdapter.addData(bean);
                    insertDataBase(bean, false);
                    recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                    qaSpeak();
                } else if (nextPage == 2) {

                    if (printText.contains(getString(R.string.quick_match))) {
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(true);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        this.nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                    } else if (printText.contains(getString(R.string.deep_match))) {
                        openDeepMatchActivity();
                    } else if (printText.contains(getString(R.string.wisdom_in_opportunity))) {
                        openWebActivity();
                    } else {
                        openIntellectSearch();
                    }
                }
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

        swipe = findViewById(R.id.activity_main_swipe);
        recyclerView = findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        qm3QAAdapter = new QM3QAAdapter(this);
        qm3QAAdapter.setMainListener(() -> {
            nextPage = 2;
            ivBack.setVisibility(View.VISIBLE);
            openIntellectSearch();
        });
        recyclerView.setAdapter(qm3QAAdapter);

        tip.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuickMatchAddActivity.class);
            Pair<View, String> p1 = new Pair<>(findViewById(R.id.activity_main_tip), "activity_main_add_tv");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, p1);
            startActivityForResult(intent, 109, options.toBundle());
        });

        swipe.setOnRefreshListener(refreshLayout -> {
            if (nextPage == 0) {
                swipe.finishRefresh();
                swipe.finishLoadMore();
                getCacheData();
            } else if (nextPage == 1) {
                swipe.finishRefresh();
                swipe.finishLoadMore();
                getCacheData();
            } else if (nextPage == 2) {
                swipe.finishRefresh();
                swipe.finishLoadMore();
            }
        });

        ivSay = findViewById(R.id.activity_main_speak);
        ivSay.setOnClickListener(v -> {

            ivQAUserBack.setVisibility(View.GONE);
            aimerShouldSay = true;

            //检查应用必备权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] strings = PermissionUtils.hasPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (strings.length > 0) {
                    ActivityCompat.requestPermissions(MainActivity.this, strings, SPEAK_PERMISSION);
                } else {
                    ivSay();
                }
            } else {
                ivSay();
            }
        });

        webView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = webView.getSettings();
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //不使用cache 全部从网络上获取
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //无模式选择，通过setAppCacheEnabled(boolean flag)设置是否打开。默认关闭，即，H5的缓存无法使用。
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        //设置支持HTML5本地存储
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webView.addJavascriptInterface(new JavaScriptinterface(this), getString(R.string.android));
        //设置Web视图
        webView.setWebChromeClient(new webChromeClient());
        webView.setWebViewClient(new webViewClient());
        webView.setVisibility(View.GONE);

        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 100);
        load = soundPool.load(this, R.raw.icon_click, 1);

        showOrHideGvBottom(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        topText.setText(getString(R.string.secret_match_project));
        webView.setVisibility(View.GONE);
        qm3QAAdapter.clearData();
        QM3QABean b = createTimeBean();
        shouldAddDBNumber = false;
        dbCount++;
        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);
        insertDataBase(b, false);
        qm3QAAdapter.addData(b);
        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
        projectId = "";
        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
        setMengCengVisible(View.VISIBLE);
        boolean isSpeak = getIntent().getBooleanExtra(getString(R.string.ISSPEAK), true);
        qaStart(isSpeak);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SPEAK_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ivSay();
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
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

        rlMengceng2.setVisibility(View.VISIBLE);
        rlMengceng2.setAlpha(0);
        rlMengceng2.animate().alpha(0).alphaBy(1).setDuration(600).start();

        new Handler().postDelayed(() -> {

            userCannel = false;

            // 移动数据分析，收集开始听写事件
            FlowerCollector.onEvent(MainActivity.this, "iat_recognize");

            mIatResults.clear();
            // 设置参数
            setUserSpeakParams();

            mTts.stopSpeaking();

            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showToast("听写失败");
            }
        }, 400);
    }

    private void showOrHideGvBottom(int visiable) {
        if (visiable == View.VISIBLE) {
            setMyTitleColor(0xcc000000, false);
        } else {
            setMyTitleColor();
        }
        gvBottom.setVisibility(visiable);
        gvBottomMengCeng1.setVisibility(visiable);
        gvBottomMengCeng2.setVisibility(visiable);
    }


    private class webViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingFragment(R.id.activity_main_fl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            removeLoadingFragment();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            removeLoadingFragment();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            removeLoadingFragment();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false;
            }
            if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www")) {
                view.loadUrl(url);
                urlList.add(url);
                isRemoveLastUrl = false;
                return false;
            } else {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest resourceRequest) {
            String url = resourceRequest.getUrl().toString();
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false;
            }
            if (url.startsWith("http") || url.startsWith("https") || url.startsWith("www")) {
                view.loadUrl(resourceRequest.getUrl().toString());
                urlList.add(resourceRequest.getUrl().toString());
                isRemoveLastUrl = false;
            }
            return true;
        }
    }

    //Web视图
    private class webChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String titleString) {
            super.onReceivedTitle(view, titleString);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void qaSpeak() {
        gogogo = true;

        removeMengCeng();

        QASpeakRequest qaSpeakRequest = new QASpeakRequest();
        qaSpeakRequest.text = printText;
        qaSpeakRequest.projectId = projectId;
        qaSpeakRequest.project_price = project_price;
        qaSpeakRequest.project_profit = project_profit;
        project_price = "";
        project_profit = "";
        HttpHelper.qaSpeak(qaSpeakRequest).subscribe(response -> {

            projectId = response.data.projectId;
            if (!TextUtils.isEmpty(response.data.searchKey)) {
                printText = response.data.searchKey;
            }

            if (response.data.status == 0) {//对话进行中

                if (AppApplication.language.equals(getString(R.string.CHINESE))) {//中文版
                    StringBuilder aimerText = new StringBuilder();
                    for (int i = 0; i < response.data.sentence.size(); i++) {
                        aimerText.append(response.data.sentence.get(i));
                    }

                    if (TextUtils.isEmpty(aimerText.toString())) {
                        aimerText.append(getString(R.string.aimer_hint_answer));
                    }

                    QM3QABean bean = new QM3QABean();
                    bean.code = getString(R.string.RECEIVE);
                    bean.name = aimerText.toString();
                    qm3QAAdapter.addData(bean);
                    insertDataBase(bean, false);
                    recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                    if (response.data.askPrice)
                        isShowMoneyFragment = true;
                    speak(aimerText.toString());
                } else {//英文版
                    StringBuilder aimerText = new StringBuilder();
                    for (int i = 0; i < response.data.sentence.size(); i++) {
                        aimerText.append(response.data.sentence.get(i));
                        QM3QABean bean = new QM3QABean();
                        bean.code = getString(R.string.RECEIVE);
                        bean.name = response.data.sentence.get(i);
                        qm3QAAdapter.addData(bean);
                        insertDataBase(bean, false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                    }

                    if (TextUtils.isEmpty(aimerText.toString())) {
                        aimerText.append(getString(R.string.aimer_hint_answer));

                        QM3QABean bean = new QM3QABean();
                        bean.code = getString(R.string.RECEIVE);
                        bean.name = aimerText.toString();
                        qm3QAAdapter.addData(bean);
                        insertDataBase(bean, false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                    }

                    if (response.data.askPrice)
                        isShowMoneyFragment = true;
                    speak(aimerText.toString());
                }

            } else if (response.data.status == 1) {//对话结束

                this.qaEndResponse = response;

                projectId = "";
                shouldAddDBNumber = true;
                ivQAUserBack.setVisibility(View.VISIBLE);

                if (response.data.relationProject == null || response.data.relationProject.size() == 0) {//对话结束 没有结果
                    QM3QABean bean = new QM3QABean();
                    bean.code = getString(R.string.RECEIVE);
                    bean.name = getString(R.string.sorry_there_is_no_suitable_project_for_you_right_now);
                    qm3QAAdapter.addData(bean);
                    insertDataBase(bean, true);
                    recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                    speak(getString(R.string.sorry_there_is_no_suitable_project_for_you_right_now));

                } else {//对话结束  有结果

                    if (response.data != null && response.data.relationProject != null && response.data.relationProject.size() > 0) {

                        if (AppApplication.language.equals(getString(R.string.CHINESE))) {//中文版
                            StringBuilder aimerText = new StringBuilder();
                            for (int i = 0; i < response.data.sentence.size(); i++) {
                                aimerText.append(response.data.sentence.get(i));
                            }

                            if (TextUtils.isEmpty(aimerText.toString())) {
                                aimerText.append(getString(R.string.aimer_hint_answer));
                            }

                            QM3QABean bean = new QM3QABean();
                            bean.code = getString(R.string.RECEIVE);
                            bean.name = aimerText.toString();
                            qm3QAAdapter.addData(bean);
                            insertDataBase(bean, false);
                            recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                            speak(aimerText.toString());
                        } else {//英文版
                            StringBuilder aimerText = new StringBuilder();
                            for (int i = 0; i < response.data.sentence.size(); i++) {
                                aimerText.append(response.data.sentence.get(i));
                                QM3QABean bean = new QM3QABean();
                                bean.code = getString(R.string.RECEIVE);
                                bean.name = response.data.sentence.get(i);
                                qm3QAAdapter.addData(bean);
                                insertDataBase(bean, false);
                                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                            }

                            if (TextUtils.isEmpty(aimerText.toString())) {
                                aimerText.append(getString(R.string.aimer_hint_answer));
                            }

                            speak(aimerText.toString());
                        }

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

            } else if (response.data.status == 2) {

                projectId = "";
                shouldAddDBNumber = true;

                if (AppApplication.language.equals(getString(R.string.CHINESE))) {//中文版
                    StringBuilder aimerText = new StringBuilder();
                    for (int i = 0; i < response.data.sentence.size(); i++) {
                        aimerText.append(response.data.sentence.get(i));
                    }

                    if (TextUtils.isEmpty(aimerText.toString())) {
                        aimerText.append(getString(R.string.aimer_hint_answer));
                    }
                    QM3QABean bean = new QM3QABean();
                    bean.code = getString(R.string.RECEIVE);
                    bean.name = aimerText.toString();
                    qm3QAAdapter.addData(bean);
                    insertDataBase(bean, true);
                    recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                    speak(aimerText.toString());
                } else {//英文版
                    StringBuilder aimerText = new StringBuilder();
                    for (int i = 0; i < response.data.sentence.size(); i++) {
                        aimerText.append(response.data.sentence.get(i));
                        QM3QABean bean = new QM3QABean();
                        bean.code = getString(R.string.RECEIVE);
                        bean.name = response.data.sentence.get(i);
                        qm3QAAdapter.addData(bean);
                        insertDataBase(bean, true);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                    }

                    if (TextUtils.isEmpty(aimerText.toString())) {
                        aimerText.append(getString(R.string.aimer_hint_answer));
                    }

                    speak(aimerText.toString());
                }
            }

            removeLoadingFragment();
        }, throwable -> {
            removeLoadingFragment();
            showThrowable(throwable);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String TYPE = intent.getStringExtra(getString(R.string.TYPE));
        if (getString(R.string.TYPE_0).equals(TYPE)) {//重新匹配的返回值
            printText = intent.getStringExtra(getString(R.string.TITLE));
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
            printText = intent.getStringExtra(getString(R.string.TITLE));
            MatchCompanyResponse response = (MatchCompanyResponse) intent.getSerializableExtra(getString(R.string.DATA));

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

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
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

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.d("MDA", "初始化失败,错误码：" + code);
        }
        // 初始化成功，之后可以调用startSpeaking方法
        // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
        // 正确的做法是将onCreate中的startSpeaking调用移至这里
    };

    private void setAimerSpeakParams() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            String speed_preference = PreferencesKeeper.getString(this, "speed_preference");
            if (TextUtils.isEmpty(speed_preference)) {
                speed_preference = "50";
            }
            mTts.setParameter(SpeechConstant.SPEED, speed_preference);
            //设置合成音调
            String pitch_preference = PreferencesKeeper.getString(this, "pitch_preference");
            if (TextUtils.isEmpty(pitch_preference)) {
                pitch_preference = "55";
            }
            mTts.setParameter(SpeechConstant.PITCH, pitch_preference);
            //设置合成音量
            String volume_preference = PreferencesKeeper.getString(this, "volume_preference");
            if (TextUtils.isEmpty(volume_preference)) {
                volume_preference = "50";
            }
            mTts.setParameter(SpeechConstant.VOLUME, volume_preference);
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /*
             * 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        String stream_preference = PreferencesKeeper.getString(this, "stream_preference");
        if (TextUtils.isEmpty(stream_preference)) {
            stream_preference = "3";
        }
        mTts.setParameter(SpeechConstant.STREAM_TYPE, stream_preference);
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
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

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            showToast("语音初始化失败");
        }
    };

    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // 合成进度  percent
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度  percent
            if (isShowMoneyFragment && percent > 50) {
                isShowMoneyFragment = false;
                showMoneyFragment();
            }
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {
                showToast(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
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
        tvMengCeng.setText(resultBuffer.toString());

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

                if (nextPage == 0) {

                    gogogo = true;
                    removeMengCeng();

                    if (printText.contains(getString(R.string.quick_match))) {
                        showOrHideGvBottom(View.GONE);
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(true);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        this.nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                    } else if (printText.contains(getString(R.string.deep_match))) {
                        openDeepMatchActivity();
                    } else if (printText.contains(getString(R.string.wisdom_in_opportunity))) {
                        openWebActivity();
                    } else {
                        showOrHideGvBottom(View.GONE);
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        this.nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                        QM3QABean bean = new QM3QABean();
                        bean.code = getString(R.string.SEND);
                        bean.name = printText;
                        qm3QAAdapter.addData(bean);
                        insertDataBase(bean, false);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                        qaSpeak();
                    }

                } else if (nextPage == 1) {

                    if (shouldAddDBNumber) {
                        shouldAddDBNumber = false;
                        dbCount++;
                    }
                    QM3QABean bean = new QM3QABean();
                    bean.code = getString(R.string.SEND);
                    bean.name = printText;
                    qm3QAAdapter.addData(bean);
                    insertDataBase(bean, false);
                    recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                    qaSpeak();
                } else if (nextPage == 2) {

                    gogogo = true;

                    removeMengCeng();

                    if (printText.contains(getString(R.string.quick_match))) {
                        showOrHideGvBottom(View.GONE);
                        ivBack.setVisibility(View.VISIBLE);
                        topText.setText(getString(R.string.secret_match_project));
                        webView.setVisibility(View.GONE);
                        qm3QAAdapter.clearData();
                        QM3QABean b = createTimeBean();

                        shouldAddDBNumber = false;
                        dbCount++;
                        DBUtilsKt.putLong(this, loginResponse.model.uuid + getString(R.string.dbCount), dbCount);

                        insertDataBase(b, false);
                        qm3QAAdapter.addData(b);
                        qaStart(true);
                        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
                        projectId = "";
                        this.nextPage = 1;
                        mengceng2View.setBackgroundColor(ContextCompat.getColor(this, R.color.transform));
                        setMengCengVisible(View.GONE);
                    } else if (printText.contains(getString(R.string.deep_match))) {
                        openDeepMatchActivity();
                    } else if (printText.contains(getString(R.string.wisdom_in_opportunity))) {
                        openWebActivity();
                    } else {
                        openIntellectSearch();
                    }

                }
            }, 1000);
        }

    }

    public void removeMengCeng() {
        AnimationUtils.alpha10Animate(rlMengceng2, 1000, () -> {
            rlMengceng2.setAlpha(0);
            rlMengceng2.setVisibility(View.GONE);
            tvMengCeng.setText("");
        });
    }

    protected void onShowKeyboard(int keyboardHeight) {
        showOrHideGvBottom(View.GONE);
        recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);
    }

    protected void onHideKeyboard() {
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (nextPage == 1) {
                if (nowTip >= textTipQuick.length) {
                    nowTip = 0;
                }
                etInput.setHint(textTipQuick[nowTip]);
                nowTip++;
                handler.sendEmptyMessageDelayed(0, 4000);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109 && resultCode == 110) {
            MatchCompanyResponse response = (MatchCompanyResponse) data.getSerializableExtra(getString(R.string.result));
            String title = data.getStringExtra(getString(R.string.TITLE));
            QM3QABean cb = new QM3QABean();
            cb.code = getString(R.string.SEND);
            cb.name = title;
            printText = title;
            qm3QAAdapter.addData(cb);
            QM3QABean cb2 = new QM3QABean();
            cb2.code = getString(R.string.RECEIVE);
            cb2.name = getString(R.string.main_text);
            qm3QAAdapter.addData(cb2);
            QM3QABean cb3 = new QM3QABean();
            cb3.code = getString(R.string.ZPXM);
            cb3.name = getString(R.string.main_text);
            cb3.data = response.data;
            cb3.type = getString(R.string.QUICK);
            qm3QAAdapter.addData(cb3);
            ivQAUserBack.setVisibility(View.VISIBLE);
        } else if (requestCode == 1000 && resultCode == 1001) {
            finish();
        }
    }

    private void speak(String string) {

        if (!aimerShouldSay) {
            return;
        }

        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(this, "tts_play");

        // 设置参数
        setAimerSpeakParams();
        int code = mTts.startSpeaking(string, mTtsListener);

        if (code != ErrorCode.SUCCESS) {
            Log.d("MDA", "语音合成失败,错误码: " + code);
        }
    }

    public QM3QABean createTimeBean() {
        QM3QABean b = new QM3QABean();
        b.code = getString(R.string.TIME);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String format = sdf.format(new Date());
        String[] split = format.split(":");
        if (Integer.parseInt(split[0]) >= 12) {
            b.name = "PM " + (Integer.parseInt(split[0]) - 12) + ":" + split[1];
        } else {
            b.name = "AM " + split[0] + ":" + split[1];
        }
        return b;
    }

    public void showMoneyFragment() {
        hideKeyBoard();
        QAMoneyFragment mbf1 = new QAMoneyFragment(MainActivity.this, new QAMoneyFragment.QAMoneyFragmentListener() {
            @Override
            public void priceOk(ArrayList<MoneyFragmentDto> list) {

                MoneyFragmentDto b0 = list.get(0);
                MoneyFragmentDto b1 = list.get(1);
                StringBuilder sb = new StringBuilder();
                if (TextUtils.isEmpty(b0.price) || b0.price.contains(getString(R.string.unlimited))) {
                    sb.append(getString(R.string.unlimited));
                } else {
                    if ("wan".equals(b0.beishu)) {
                        sb.append(getString(R.string.amount_of_the_transaction)).append(b0.price).append(getString(R.string.wan_and));
                        project_price = b0.price + getString(R.string.wan);
                    } else {
                        sb.append(getString(R.string.amount_of_the_transaction)).append(b0.price).append(getString(R.string.yi_and));
                        project_price = b0.price + getString(R.string.yi);
                    }
                }
                if (TextUtils.isEmpty(b1.price) || b1.price.contains(getString(R.string.unlimited))) {
                    sb.append(getString(R.string.unearned_profit_before_interest_and_taxes));
                } else {
                    if ("wan".equals(b1.beishu)) {
                        sb.append(getString(R.string.ebit)).append(b1.price).append(getString(R.string.wan));
                        project_profit = b1.price + getString(R.string.wan);
                    } else {
                        sb.append(getString(R.string.ebit)).append(b1.price).append(getString(R.string.yi));
                        project_profit = b1.price + getString(R.string.yi);
                    }
                }
                printText = sb.toString();

                QM3QABean bean = new QM3QABean();
                bean.code = getString(R.string.SEND);
                bean.name = printText;
                qm3QAAdapter.addData(bean);
                insertDataBase(bean, false);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                qaSpeak();
            }

            @Override
            public void priceCannel() {
                printText = getString(R.string.i_have_no_requerement);
                QM3QABean bean = new QM3QABean();
                bean.code = getString(R.string.SEND);
                bean.name = printText;
                qm3QAAdapter.addData(bean);
                insertDataBase(bean, false);
                recyclerView.scrollToPosition(qm3QAAdapter.getItemCount() - 1);

                qaSpeak();
            }
        }, 0);
        mbf1.show(getSupportFragmentManager(), "mbf");
    }

    public void insertDataBase(QM3QABean bean, boolean isClear) {
        bean.speakCount = dbCount;
        dbList.add(bean);
        updateDb(isClear);
    }

    public void qaStart(boolean isSpeak) {
        QM3QABean bean = new QM3QABean();
        bean.code = getString(R.string.RECEIVE);
        bean.name = getString(R.string.what_do_you_need);
        qm3QAAdapter.addData(bean);
        if (isSpeak) {
            speak(getString(R.string.what_do_you_need));
        }
        insertDataBase(bean, false);
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

    public void openDeepMatchActivity() {
        LoginResponse response = DBUtilsKt.getLoginResponse(this);
        if (response.model.authentication) {
            Intent intent = new Intent(MainActivity.this, DeepMatchActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NotVerificationActivity.class);
            intent.putExtra(getString(R.string.DATA), getString(R.string.TYPE_0));
            startActivity(intent);
        }
    }

    public void initIjkfly() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
    }

    //智配关系
    public void openWebActivity() {
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("params", "https://match.buyint.com");
        intent.putExtra(getString(R.string.TITLE), getString(R.string.match_capital_market_relation));
        startActivity(intent);
    }

    //智配关系
    public void openIntellectSearch() {
        StatService.onEvent(this, getString(R.string.ZSXM), "从首页跳转智搜项目并匹配结果", 1);

        if (webView.getVisibility() != View.VISIBLE) {
            webView.setVisibility(View.VISIBLE);
        }
        hideKeyBoard();
        webView.loadUrl("https://obor.buyint.com/#/results/app" + "?q=" + printText);
        urlList.add("https://obor.buyint.com/#/results/app" + "?q=" + printText);
        isRemoveLastUrl = true;
    }

    public void setMengCengVisible(int isVisible) {
        mengceng2Text.setVisibility(isVisible);
        mengceng2Text1.setVisibility(isVisible);
        mengceng2Text2.setVisibility(isVisible);
        mengceng2Text3.setVisibility(isVisible);
        mengceng2Text4.setVisibility(isVisible);
    }
}
