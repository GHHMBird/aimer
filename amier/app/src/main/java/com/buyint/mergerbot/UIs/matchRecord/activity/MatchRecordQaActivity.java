package com.buyint.mergerbot.UIs.matchRecord.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.adapter.BttomGridAdapter;
import com.buyint.mergerbot.Utility.AnimationUtils;
import com.buyint.mergerbot.Utility.JsonParser;
import com.buyint.mergerbot.Utility.PermissionUtils;
import com.buyint.mergerbot.base.AppApplication;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.MatchRecordResultBean;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.DeleteEditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by huheming on 2018/10/11
 */

public class MatchRecordQaActivity extends BaseActivity {

    private VoiceLineView vl;
    private DeleteEditText etInput;
    private ImageView ivSay, ivNine;
    private GridView gvBottom;
    private RecyclerView recyclerView;
    private TextView title, tvSend;
    private FloatingActionsMenu fam;
    private RelativeLayout rlMengceng;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitleWhiteAndTextBlank();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_record_qa);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initIjkfly();

        recyclerView = findViewById(R.id.activity_match_record_qa_recycler);
        ivSay = findViewById(R.id.activity_match_record_qa_speak);
        etInput = findViewById(R.id.activity_match_record_qa_et);
        tvSend = findViewById(R.id.activity_match_record_qa_tv_send);
        ivNine = findViewById(R.id.activity_match_record_qa_iv_nine);
        gvBottom = findViewById(R.id.activity_match_record_qa_bottom_grid);
        fam = findViewById(R.id.activity_match_record_qa_fam);
        rlMengceng = findViewById(R.id.activity_match_record_qa_mengceng2_center_view);
        vl = findViewById(R.id.activity_match_record_qa_voiceline);

        attachKeyboardListeners();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        title = findViewById(R.id.toolbar_white_title);
        title.setText(getString(R.string.match_record));
        findViewById(R.id.toolbar_white_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.toolbar_white_right_add_rl).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_right_search_rl).setVisibility(View.GONE);

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
                    ActivityCompat.requestPermissions(MatchRecordQaActivity.this, strings, SPEAK_PERMISSION);
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
                Intent intent = new Intent(MatchRecordQaActivity.this, MatchRecordDeepSearchActivity.class);
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1));
                startActivity(intent);
                fam.collapse();
                fam.setButtonIcon(R.mipmap.match_record_fam_ic);
            }

            @Override
            public void onMenuCollapsed() {
                fam.setButtonIcon(R.mipmap.match_record_fam_ic);
            }
        });

        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 100);
        load = soundPool.load(this, R.raw.icon_click, 1);


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
        showLoadingFragment(R.id.activity_match_record_qa_fl);
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
            FlowerCollector.onEvent(MatchRecordQaActivity.this, "iat_recognize");

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

}
