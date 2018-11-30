package com.buyint.mergerbot.UIs.match.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.WebActivity;
import com.buyint.mergerbot.UIs.main.activity.MatchProjectBackActivity;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.MatchDetailAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.MatchDetailPeopleAdapter;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.AppApplication;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.BooleanResponse;
import com.buyint.mergerbot.dto.MatchCompanyBean;
import com.buyint.mergerbot.dto.MatchCompanyDetailBean;
import com.buyint.mergerbot.dto.MatchCompanyDetailResponse;
import com.buyint.mergerbot.dto.MatchRateBean;
import com.buyint.mergerbot.interfaces.IDeleteProject;
import com.buyint.mergerbot.interfaces.IGetMatchCompanyDetail;
import com.buyint.mergerbot.interfaces.INotNoticeProject;
import com.buyint.mergerbot.interfaces.INoticeProject;
import com.buyint.mergerbot.presenter.MatchDetailPresenter;
import com.buyint.mergerbot.stroage.PreferencesKeeper;
import com.buyint.mergerbot.view.chart.charts.PieChart;
import com.buyint.mergerbot.view.chart.data.Entry;
import com.buyint.mergerbot.view.chart.data.PieData;
import com.buyint.mergerbot.view.chart.data.PieDataSet;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.scwang.smartrefresh.header.StoreHouseHeader;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CXC on 2018/4/23
 */

public class MatchDetailActivity extends BaseActivity implements View.OnClickListener, IGetMatchCompanyDetail, INoticeProject, INotNoticeProject, IDeleteProject {

    private PieChart pie;
    private TextView tv1;
    private TextView tv2;
    private MatchDetailAdapter adapter;
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "";
    private TextView tvLocation, tvTime, tvComeFrom, tvUserName, tvCompanyDesc, tvMoney, tvIndustry, tvTitle;
    private TextView tvType;
    private TextView ivPop;
    private String clickId;
    private RecyclerView recyclerView, peopleRecyclerview;
    private ImageView ivNoSimilar, userIcon;
    private MatchDetailPresenter presenter;
    private MatchCompanyDetailBean data;
    private int clickColor;
    private View tab1View;
    private View tab2View;
    private TextView tab1Tv;
    private TextView tab2Tv;
    private NestedScrollView scroll;
    private float maxHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        maxHeight = dp2px(150, this);

        presenter = new MatchDetailPresenter(this, this, this, this);
        if (AppApplication.language.equals(getString(R.string.CHINESE))) {
            voicer = getString(R.string.xiaoyan);
        } else {
            voicer = getString(R.string.kaiselin);
        }

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(MatchDetailActivity.this, mTtsInitListener);

        setMyTitleColor(themeColor, true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        tab1View = findViewById(R.id.activity_match_detail_tab1_view);
        tab2View = findViewById(R.id.activity_match_detail_tab2_view);
        tab1Tv = findViewById(R.id.activity_match_detail_tab1_tv);
        tab2Tv = findViewById(R.id.activity_match_detail_tab2_tv);
        clickColor = tab1Tv.getCurrentTextColor();
        tab1Tv.setOnClickListener(this);
        tab2Tv.setOnClickListener(this);

        StoreHouseHeader shh = findViewById(R.id.storeHouseHeader);
        shh.setPrimaryColors(clickColor);

        LinearLayout tabLl = findViewById(R.id.activity_match_detail_tab);

        View titleView = findViewById(R.id.activity_match_detail_toolbar);
        titleView.setAlpha(0);
        scroll = findViewById(R.id.activity_match_detail_scroll);
        scroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            titleView.setAlpha(scrollY / maxHeight);
            if (scrollY > maxHeight) {
                tabLl.setVisibility(View.VISIBLE);
            } else {
                tabLl.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.toolbar_white_back).setOnClickListener(v -> onBackPressed());

        pie = findViewById(R.id.layout_match_analyse_piechart);
        pie.setDescription("");
//        pie.setDrawSliceText(false);   //设置隐藏饼状图上文字，只显示百分比
        pie.setDrawHoleEnabled(true);//是否显示中间的洞
        pie.setCenterTextSize(20);
        pie.setHoleColor(Color.WHITE);//中间洞颜色
        pie.setHoleRadius(99.8f);//中间洞半径(按百分比)
        pie.setDrawCenterText(true);//是否显示洞中间文本

        //触摸是否可以旋转以及松手后旋转的角度
        pie.setRotationEnabled(false);

        pie.getLegend().setEnabled(false);

        handler.sendEmptyMessage(10);

        ImageView iv = findViewById(R.id.layout_match_analyse_iv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotation);
        iv.setAnimation(animation);
        animation.start();

        tv1 = findViewById(R.id.layout_match_list_tv1);
        tv2 = findViewById(R.id.layout_match_list_tv2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);

        recyclerView = findViewById(R.id.layout_match_list_recyclerview);
        peopleRecyclerview = findViewById(R.id.layout_match_related_master_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.layout_company_detail_iv).setOnClickListener(v -> {

            // 移动数据分析，收集开始合成事件
            FlowerCollector.onEvent(MatchDetailActivity.this, "tts_play");

            String text = tvCompanyDesc.getText().toString();
            // 设置参数
            setParam();
            int code = mTts.startSpeaking(text, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

            if (code != ErrorCode.SUCCESS) {
//                    showToast("语音合成失败");
                Log.d("MDA", "语音合成失败,错误码: " + code);
            }

        });

        initView();
        showLoadingFragment(R.id.activity_match_detail_fl);
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.activity_match_detail_title);
        ivPop = findViewById(R.id.toolbar_right);
        Drawable drawable = getResources().getDrawable(R.mipmap.three_cicle_white);
        drawable.setBounds(0, 0, dp2px(16f), dp2px(4f));
        ivPop.setCompoundDrawables(null, null, drawable, null);
        tvLocation = findViewById(R.id.activity_match_detail_location);
        tvIndustry = findViewById(R.id.activity_match_detail_industry);
        tvType = findViewById(R.id.activity_match_detail_type);
        tvMoney = findViewById(R.id.activity_match_detail_money);
        tvUserName = findViewById(R.id.activity_match_detail_username);
        tvCompanyDesc = findViewById(R.id.layout_company_detail_tv);
        tvTime = findViewById(R.id.activity_match_detail_time);
        tvComeFrom = findViewById(R.id.activity_match_detail_comefrom);
        ivNoSimilar = findViewById(R.id.layout_match_list_image);
        userIcon = findViewById(R.id.toolbar_image_image);
    }

    private void initData() {
        clickId = getIntent().getStringExtra(getString(R.string.CLICK_ID));
        String proId = getIntent().getStringExtra(getString(R.string.PROID));
        String type = getIntent().getStringExtra(getString(R.string.TYPE));

        presenter.getMatchCompanyDetail(clickId, proId, type);
    }

    private void setData() {

//        Glide.with(this).load(data.userAvatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(new BitmapImageViewTarget(userIcon) {
//            @Override
//            public void setResource(Bitmap resource) {
//                RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(getResources(), resource);
//                rbd.setCircular(true);
//                userIcon.setImageDrawable(rbd);
//            }
//        });

        ((TextView) findViewById(R.id.toolbar_white_title)).setText(TextUtils.isEmpty(data.pojectName) ? getString(R.string.guess) : data.pojectName);
        tvTitle.setText(TextUtils.isEmpty(data.pojectName) ? getString(R.string.guess) : data.pojectName);
        tvCompanyDesc.setText(TextUtils.isEmpty(data.description) ? getString(R.string.guess) : data.description);
        tvUserName.setText(TextUtils.isEmpty(data.userName) ? "Aimer采集分析" : data.userName);
        tvMoney.setText(TextUtils.isEmpty(data.price) ? getString(R.string.price_not_show) : data.price);
        tvLocation.setText(TextUtils.isEmpty(data.location) ? getString(R.string.guess) : data.location);
        tvIndustry.setText(TextUtils.isEmpty(data.industryClassification) ? getString(R.string.guess) : data.industryClassification);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date(data.createdTime));
        tvTime.setText(format);

//        tvComeFrom.setText(data.projectSource);
        if (getString(R.string.TYPE_0101).equals(data.intention)) {
            tvType.setText(getString(R.string.acquisition));
        } else if (getString(R.string.TYPE_0102).equals(data.intention)) {
            tvType.setText(getString(R.string.sell));
        } else if (getString(R.string.TYPE_010301).equals(data.intention)) {
            tvType.setText(getString(R.string.investment));
        } else if (getString(R.string.TYPE_010302).equals(data.intention)) {
            tvType.setText(getString(R.string.financing));
        }

        if (!TextUtils.isEmpty(data.webSite)) {
            findViewById(R.id.activity_match_detail_comefrom_pic).setVisibility(View.VISIBLE);
            tvComeFrom.setText(data.sourceWeb);

            tvComeFrom.setOnClickListener(v -> {
                Intent intent = new Intent(MatchDetailActivity.this, WebActivity.class);
                intent.putExtra("params", data.webSite);
                intent.putExtra(getString(R.string.TITLE), "");
                startActivity(intent);
            });

        }

        int comeFromType = getIntent().getIntExtra(getString(R.string.TYPE), 0);//0  其他普通页面  1 我的发布

        ivPop.setOnClickListener(v -> {
            if (comeFromType == 0) {
                if (data.attention) {
                    //取关
                    ViewHelper.showNoticePopupWindowAuto(this, ivPop, 1, v15 -> presenter.notNoticeProject(clickId), v116 -> {
                        Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                        intent.putExtra(getString(R.string.DATA), clickId);
                        startActivity(intent);
                    });
                } else {
                    //关注
                    ViewHelper.showNoticePopupWindowAuto(this, ivPop, 0, v14 -> presenter.noticeProject(clickId), v112 -> {
                        Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                        intent.putExtra(getString(R.string.DATA), clickId);
                        startActivity(intent);
                    });
                }
            } else if (comeFromType == 1) {
                ViewHelper.showNoticePopupWindowAuto(this, ivPop, 2, v1 -> presenter.deleteProject(clickId), v111 -> {
                    Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                    intent.putExtra(getString(R.string.DATA), clickId);
                    startActivity(intent);
                });
            }
        });
        findViewById(R.id.toolbar_white_right_add_rl).setOnClickListener(v -> {
            if (comeFromType == 0) {
                if (data.attention) {
                    //取关
                    ViewHelper.showNoticePopupWindowAuto(this, ivPop, 1, v15 -> presenter.notNoticeProject(clickId), v113 -> {
                        Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                        intent.putExtra(getString(R.string.DATA), clickId);
                        startActivity(intent);
                    });
                } else {
                    //关注
                    ViewHelper.showNoticePopupWindowAuto(this, ivPop, 0, v14 -> presenter.noticeProject(clickId), v114 -> {
                        Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                        intent.putExtra(getString(R.string.DATA), clickId);
                        startActivity(intent);
                    });
                }
            } else if (comeFromType == 1) {
                ViewHelper.showNoticePopupWindowAuto(this, ivPop, 2, v1 -> presenter.deleteProject(clickId), v115 -> {
                    Intent intent = new Intent(MatchDetailActivity.this, MatchProjectBackActivity.class);
                    intent.putExtra(getString(R.string.DATA), clickId);
                    startActivity(intent);
                });
            }
        });

        adapter = new MatchDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(data.companyInfoDtoList == null ? new ArrayList<>() : data.companyInfoDtoList, data.institutionDtoList == null ? new ArrayList<>() : data.institutionDtoList);
        int i = adapter.setType(0);
        if (i == 0) {
            ivNoSimilar.setImageResource(R.mipmap.no_similar_company);
            ivNoSimilar.setVisibility(View.VISIBLE);
        } else {
            ivNoSimilar.setVisibility(View.GONE);
        }

        if (data.industrySuperiorList != null && data.industrySuperiorList.size() > 0) {
            //todo
            findViewById(R.id.activity_match_detail_people_ll).setVisibility(View.GONE);
            peopleRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            peopleRecyclerview.setAdapter(new MatchDetailPeopleAdapter(this, data.industrySuperiorList));
            peopleRecyclerview.setNestedScrollingEnabled(false);
        } else {
            findViewById(R.id.activity_match_detail_people_ll).setVisibility(View.GONE);
        }

        MatchRateBean matchRateObj = data.matchRateObj;

        if (matchRateObj == null || matchRateObj.matchRate <= 0) {
            findViewById(R.id.activity_match_detail_piechart_ll).setVisibility(View.GONE);
        } else {
            double matchRate = matchRateObj.matchRate;
            double industryRate = matchRateObj.industryRate;
            double priceRate = matchRateObj.priceRate;
            double productRate = matchRateObj.productRate;

            TextView tv = findViewById(R.id.layout_match_analyse_tv);
            TextView tvv = findViewById(R.id.layout_match_analyse_tv2);
            tv.setText(new BigDecimal(matchRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + "%");
            tvv.setText(getString(R.string.comprehensive_matching_degree));
            TextView tvIndustry = findViewById(R.id.layout_match_analyse_industry);
            TextView tvPrice = findViewById(R.id.layout_match_analyse_price);
            TextView tvProduct = findViewById(R.id.layout_match_analyse_product);

            ArrayList<String> li = new ArrayList<>();
            ArrayList<Entry> entrys = new ArrayList();
            if (industryRate > 0) {
                li.add(getString(R.string.industry));
                tvIndustry.setText(new BigDecimal(industryRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + "%");
                entrys.add(new Entry(new BigDecimal(industryRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue(), 32));
            } else {
                findViewById(R.id.layout_match_analyse_industry_ll).setVisibility(View.GONE);
            }
            if (priceRate > 0) {
                li.add(getString(R.string.price));
                tvPrice.setText(new BigDecimal(priceRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + "%");
                entrys.add(new Entry(new BigDecimal(priceRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue(), 32));
            } else {
                findViewById(R.id.layout_match_analyse_price_ll).setVisibility(View.GONE);
            }
            if (productRate > 0) {
                li.add(getString(R.string.product));
                tvProduct.setText(new BigDecimal(productRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + "%");
                entrys.add(new Entry(new BigDecimal(productRate * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue(), 32));
            } else {
                findViewById(R.id.layout_match_analyse_product_ll).setVisibility(View.GONE);
            }

            String[] strings = li.toArray(new String[li.size()]);

            PieDataSet dataSet = new PieDataSet(entrys, "");
            dataSet.setValueTextSize(8);
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(this, R.color.color_cccccc));
            dataSet.setColors(colors);

            PieData pieData = new PieData(strings, dataSet);
            pieData.setDrawValues(true);
            pie.setData(pieData);
            pie.invalidate();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            pie.setRotationAngle(what % 360);
            pie.invalidate();
            if (what > 36000) {
                what = 0;
            }
            handler.sendEmptyMessageDelayed(what + 1, 100);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_match_list_tv1:
                tv1.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
                tv2.setTextColor(ContextCompat.getColor(this, R.color.color_cccccc));
                tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.drawable.md_app_color));
                tv2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.drawable.md_transparent));
                int i = adapter.setType(0);
                if (i == 0) {
                    ivNoSimilar.setImageResource(R.mipmap.no_similar_company);
                    ivNoSimilar.setVisibility(View.VISIBLE);
                } else {
                    ivNoSimilar.setVisibility(View.GONE);
                }
                break;
            case R.id.layout_match_list_tv2:
                tv1.setTextColor(ContextCompat.getColor(this, R.color.color_cccccc));
                tv2.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
                tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.drawable.md_transparent));
                tv2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.drawable.md_app_color));
                int j = adapter.setType(1);
                if (j == 0) {
                    ivNoSimilar.setImageResource(R.mipmap.no_similar_mechanism);
                    ivNoSimilar.setVisibility(View.VISIBLE);
                } else {
                    ivNoSimilar.setVisibility(View.GONE);
                }
                break;
            case R.id.activity_match_detail_tab1_tv:
                tab1Tv.setTextColor(clickColor);
                tab2Tv.setTextColor(ContextCompat.getColor(MatchDetailActivity.this, R.color.color_999999));
                tab1View.setBackgroundColor(clickColor);
                tab2View.setBackgroundColor(Color.TRANSPARENT);
                scroll.scrollTo(0, (int) maxHeight + 1);
                break;
            case R.id.activity_match_detail_tab2_tv:
                tab1Tv.setTextColor(ContextCompat.getColor(MatchDetailActivity.this, R.color.color_999999));
                tab2Tv.setTextColor(clickColor);
                tab1View.setBackgroundColor(Color.TRANSPARENT);
                tab2View.setBackgroundColor(clickColor);
                scroll.scrollTo(0, 1000000);
                break;
        }
    }

    /**
     * 合成回调监听。
     */
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
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                //播放完成
            } else if (error != null) {
                showToast(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = code -> {
        Log.d("MAD", "InitListener init() code = " + code);
        if (code != ErrorCode.SUCCESS) {
//                showToast("初始化失败");
            Log.d("MDA", "初始化失败,错误码：" + code);
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
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
                pitch_preference = "50";
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
            /**
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
        if (presenter != null) {
            presenter.destory();
            presenter = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(MatchDetailActivity.this);
        FlowerCollector.onPageStart("MDA");

        //百度埋点
        StatService.onResume(this);

        //页面时长埋点
        StatService.onPageStart(this, getString(R.string.matchdetailactivity) + "-and-" + clickId);

        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd("MDA");
        FlowerCollector.onPause(MatchDetailActivity.this);

        //百度埋点
        StatService.onResume(this);

        //页面时长埋点
        StatService.onPageEnd(this, getString(R.string.matchdetailactivity) + "-and-" + clickId);

        super.onPause();
    }

    public float dp2px(float dpValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getMatchCompanyDetailSuccess(@NotNull MatchCompanyDetailResponse response) {
        if (response != null && response.data != null) {
            this.data = response.data;
            setData();
        }
        removeLoadingFragment();
    }

    @Override
    public void getMatchCompanyDetailFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
        removeLoadingFragment();
    }

    @Override
    public void noticeProjectSuccess(MatchCompanyBean bean, @NotNull BooleanResponse response) {
        if (response.data) {
            showToast("关注成功");
            data.attention = true;
        } else {
            showToast("关注失败，请稍后再试");
        }
    }

    @Override
    public void noticeProjectFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void notNoticeProjectSuccess(MatchCompanyBean bean, @NotNull BooleanResponse response) {
        if (response.data) {
            showToast("已取消关注");
            data.attention = false;
        } else {
            showToast("取消关注失败，请稍后再试");
        }
    }

    @Override
    public void notNoticeProjectFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void deleteProjectSuccess(@NotNull BooleanResponse response) {
        if (response.data) {
            showToast("已删除项目");
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.DATA), getIntent().getStringExtra(getString(R.string.CLICK_ID)));
            setResult(10012, intent);
            finish();
        } else {
            showToast("删除项目失败，请稍后再试");
        }
    }

    @Override
    public void deleteProjectFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }
}
