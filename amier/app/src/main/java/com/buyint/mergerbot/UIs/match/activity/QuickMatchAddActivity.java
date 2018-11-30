package com.buyint.mergerbot.UIs.match.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.match.fragment.QAMoneyFragment;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.base.LoadingFragment;
import com.buyint.mergerbot.dto.CodeNameBean;
import com.buyint.mergerbot.dto.CodeTitleBean;
import com.buyint.mergerbot.dto.MarketShareBean;
import com.buyint.mergerbot.dto.MatchCompanyRequest;
import com.buyint.mergerbot.dto.MatchCompanyRequestBean;
import com.buyint.mergerbot.dto.MoneyFragmentDto;
import com.buyint.mergerbot.dto.PublisherInfoBean;
import com.buyint.mergerbot.dto.SemanticExtractionResponse;
import com.buyint.mergerbot.dto.UnitValueBean;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.rx.RequestErrorThrowable;
import com.buyint.mergerbot.view.FluidLayout;
import com.buyint.mergerbot.dto.FluidLayoutBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 */

public class QuickMatchAddActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout styleLl, stylelll, styleLl2, styleLl3, styleLl4;
    private TextView styleTv;
    private LinearLayout styleLl1;
    private ImageView styleIv1;
    private ImageView styleIv2;
    private ImageView styleIv3;
    private ImageView styleIv4;
    private FluidLayout flPlace, flChanpin, flHangye;
    private ArrayList<CodeNameBean> placeBeanList = new ArrayList<>();
    private ArrayList<CodeTitleBean> hangyeBeanList = new ArrayList<>();
    private ArrayList<CodeNameBean> chanpinList = new ArrayList<>();
    private TextView tvGuiMo;
    public String danwei = "￥";
    private String money = "0";
    private MatchCompanyRequest request;
    private View placeLine, hangyeLine;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_match_add2);

        placeLine = findViewById(R.id.activity_quick_match_add_2_place_line);
        hangyeLine = findViewById(R.id.activity_quick_match_add_2_hangye_line);
        TextView tvGoToDeepMatch = findViewById(R.id.activity_quick_match_add2_to_deep);
        tvGoToDeepMatch.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvGoToDeepMatch.getPaint().setAntiAlias(true);
        tvGoToDeepMatch.setOnClickListener(v -> startActivity(new Intent(QuickMatchAddActivity.this, DeepMatchActivity.class)));

        findViewById(R.id.activity_quick_match_add_2_next).setOnClickListener(v -> {
            if (placeBeanList.size() == 0 && hangyeBeanList.size() == 0) {
                placeLine.setBackgroundColor(Color.RED);
                hangyeLine.setBackgroundColor(Color.RED);
                return;
            }
            if (hangyeBeanList.size() == 0) {
                hangyeLine.setBackgroundColor(Color.RED);
                return;
            }
            if (placeBeanList.size() == 0) {
                placeLine.setBackgroundColor(Color.RED);
                return;
            }
            request = new MatchCompanyRequest();
            initRequest(request);
            if (getString(R.string.acquisition).equals(styleTv.getText().toString().trim())) {
                request.intention.add(getString(R.string.TYPE_0101));//购买意图或者出售意图
                request.requirement.location = placeBeanList;
                request.requirement.product_productWords = chanpinList;
                for (int i = 0; i < hangyeBeanList.size(); i++) {
                    CodeNameBean cb = new CodeNameBean();
                    cb.name = hangyeBeanList.get(i).name.get(0);
                    cb.code = hangyeBeanList.get(i).code;
                    request.requirement.industry_Classification.add(cb);//行业列表
                }
                UnitValueBean bean = new UnitValueBean();
                bean.unit = danwei;
                bean.value = Double.parseDouble(money) * 10000;
                request.requirement.price.add(bean);//规模

                request.projectName = "我要" + styleTv.getText().toString().trim() + request.requirement.industry_Classification.get(0).name + "项目";

            } else if (getString(R.string.sell).equals(styleTv.getText().toString().trim())) {

                request.intention.add(getString(R.string.TYPE_0102));//购买意图或者出售意图
                request.condition.location = placeBeanList;//地理位置列表
                request.condition.product_productWords = chanpinList;//产品名称列表
                for (int i = 0; i < hangyeBeanList.size(); i++) {
                    CodeNameBean cb = new CodeNameBean();
                    cb.name = hangyeBeanList.get(i).name.get(0);
                    cb.code = hangyeBeanList.get(i).code;
                    request.condition.industry_Classification.add(cb);//行业列表
                }
                UnitValueBean bean = new UnitValueBean();
                bean.unit = danwei;
                bean.value = Double.parseDouble(money) * 10000;
                request.condition.price.add(bean);//规模

                request.projectName = "我要" + styleTv.getText().toString().trim() + request.condition.industry_Classification.get(0).name + "项目";

            } else if (getString(R.string.investment).equals(styleTv.getText().toString().trim())) {
                request.intention.add(getString(R.string.TYPE_010301));//购买意图或者出售意图

                request.requirement.location = placeBeanList;
                request.requirement.product_productWords = chanpinList;
                for (int i = 0; i < hangyeBeanList.size(); i++) {
                    CodeNameBean cb = new CodeNameBean();
                    cb.name = hangyeBeanList.get(i).name.get(0);
                    cb.code = hangyeBeanList.get(i).code;
                    request.requirement.industry_Classification.add(cb);//行业列表
                }
                UnitValueBean bean = new UnitValueBean();
                bean.unit = danwei;
                bean.value = Double.parseDouble(money) * 10000;
                request.requirement.price.add(bean);//规模

                request.projectName = "我要" + styleTv.getText().toString().trim() + request.requirement.industry_Classification.get(0).name + "项目";

            } else if (getString(R.string.financing).equals(styleTv.getText().toString().trim())) {
                request.intention.add(getString(R.string.TYPE_010302));//购买意图或者出售意图

                request.condition.location = placeBeanList;//地理位置列表
                request.condition.product_productWords = chanpinList;//产品名称列表
                for (int i = 0; i < hangyeBeanList.size(); i++) {
                    CodeNameBean cb = new CodeNameBean();
                    cb.name = hangyeBeanList.get(i).name.get(0);
                    cb.code = hangyeBeanList.get(i).code;
                    request.condition.industry_Classification.add(cb);//行业列表
                }
                UnitValueBean bean = new UnitValueBean();
                bean.unit = danwei;
                bean.value = Double.parseDouble(money) * 10000;
                request.condition.price.add(bean);//规模

                request.projectName = "我要" + styleTv.getText().toString().trim() + request.condition.industry_Classification.get(0).name + "项目";

            } else {
                return;
            }
            showLoadingFragment(R.id.activity_quick_match_add_2_fl);
            matchCompany();
        });

        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());

        tvTitle = findViewById(R.id.activity_quick_match_add_2_tv);
        styleTv = findViewById(R.id.activity_quick_match_add_2_style_tv);
        stylelll = findViewById(R.id.activity_quick_match_add_2_style_lll);
        styleLl1 = findViewById(R.id.activity_quick_match_add_2_style_ll1);
        styleLl2 = findViewById(R.id.activity_quick_match_add_2_style_ll2);
        styleLl3 = findViewById(R.id.activity_quick_match_add_2_style_ll3);
        styleLl4 = findViewById(R.id.activity_quick_match_add_2_style_ll4);
        styleLl = findViewById(R.id.activity_quick_match_add_2_style_ll);
        styleIv1 = findViewById(R.id.activity_quick_match_add_2_style_iv1);
        styleIv2 = findViewById(R.id.activity_quick_match_add_2_style_iv2);
        styleIv3 = findViewById(R.id.activity_quick_match_add_2_style_iv3);
        styleIv4 = findViewById(R.id.activity_quick_match_add_2_style_iv4);
        tvGuiMo = findViewById(R.id.activity_quick_match_add_2_tv_money);

        findViewById(R.id.activity_quick_match_add_2_iv_place).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 10);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 101);
        });
        findViewById(R.id.add_ll_place).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 10);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 101);
        });
        findViewById(R.id.activity_quick_match_add_2_iv_hangye).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 11);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 102);
        });
        findViewById(R.id.add_ll_hangye).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 11);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 102);
        });
        findViewById(R.id.activity_quick_match_add_2_iv_chanpin).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 12);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 103);
        });
        findViewById(R.id.add_ll_chanpin).setOnClickListener(v -> {
            Intent intent = new Intent(QuickMatchAddActivity.this, QuickMatchActivity.class);
            intent.putExtra(getString(R.string.TYPE), 12);
            intent.putExtra(getString(R.string.NAME), styleTv.getText().toString().trim());
            startActivityForResult(intent, 103);
        });

        styleLl1.setOnClickListener(this);
        styleLl2.setOnClickListener(this);
        styleLl3.setOnClickListener(this);
        styleLl4.setOnClickListener(this);
        findViewById(R.id.activity_quick_match_add_2_style_more).setOnClickListener(v -> {
            stylelll.setVisibility(View.GONE);
            styleLl.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.activity_quick_match_add_2_money_more).setOnClickListener(v -> showMoneyFragment());

        flPlace = findViewById(R.id.activity_quick_match_add_2_fl_place);
        flChanpin = findViewById(R.id.activity_quick_match_add_2_fl_chanpin);
        flHangye = findViewById(R.id.activity_quick_match_add_2_fl_hangye);

        Intent intent = getIntent();
//        String text = intent.getStringExtra(getString(R.string.text));
//        tv.setText(text);
        SemanticExtractionResponse response = (SemanticExtractionResponse) intent.getSerializableExtra(getString(R.string.DATA));
        if (response == null || response.data == null) {
            styleTv.setText("");
            stylelll.setVisibility(View.GONE);
            styleLl.setVisibility(View.VISIBLE);
        } else {
            if (getString(R.string.TYPE_0101).equals(response.data.intention)) {
                styleTv.setText(getString(R.string.acquisition));
            } else if (getString(R.string.TYPE_0102).equals(response.data.intention)) {
                styleTv.setText(getString(R.string.sell));
            } else if (getString(R.string.TYPE_010301).equals(response.data.intention)) {
                styleTv.setText(getString(R.string.investment));
            } else if (getString(R.string.TYPE_010302).equals(response.data.intention)) {
                styleTv.setText(getString(R.string.financing));
            } else {
                styleTv.setText("");
                stylelll.setVisibility(View.GONE);
                styleLl.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(response.data.scale)) {
                tvGuiMo.setText(response.data.scale);
                money = response.data.scale;
            }
            if (response.data.location != null && response.data.location.size() > 0) {
                placeBeanList.addAll(response.data.location);
                initFluidLayoutPlace(initListPlace());
            }
            if (response.data.product != null && response.data.product.size() > 0) {
                chanpinList.addAll(response.data.product);
                initFluidLayoutChanPin(initListChanPin());
            }
            if (response.data.industry != null && response.data.industry.size() > 0) {
                for (int i = 0; i < response.data.industry.size(); i++) {
                    CodeTitleBean bean = new CodeTitleBean();
                    if (bean.name == null) {
                        bean.name = new ArrayList<>();
                    } else {
                        bean.name.clear();
                    }
                    bean.code = response.data.industry.get(i).code;
                    bean.name.add(response.data.industry.get(i).name);
                    hangyeBeanList.add(bean);
                }
                initFluidLayoutHangYe(initListHangYe());
            }
        }

        if (response != null && response.data != null && !TextUtils.isEmpty(response.data.intention) && response.data.industry != null && response.data.industry.size() > 0 && response.data.location != null && response.data.location.size() > 0 && response.data.product != null && response.data.product.size() > 0) {
            tvTitle.setText(getString(R.string.please_confirm_my_demand));
        }
    }

    public void showMoneyFragment() {
        hideKeyBoard();
        QAMoneyFragment mbf1 = new QAMoneyFragment(QuickMatchAddActivity.this, new QAMoneyFragment.QAMoneyFragmentListener() {
            @Override
            public void priceOk(ArrayList<MoneyFragmentDto> list) {

                MoneyFragmentDto b0 = list.get(0);
                QuickMatchAddActivity.this.danwei = b0.danwei;
                if (b0.price.equals(getString(R.string.unlimited)) || "0".equals(b0.price)) {
                    QuickMatchAddActivity.this.money = "0";
                    tvGuiMo.setText(getString(R.string.unlimited));
                } else {
                    QuickMatchAddActivity.this.money = b0.price;
                    if (b0.beishu.equals("wan")) {
                        tvGuiMo.setText(money + "万");
                    } else if (b0.beishu.equals("yi")) {
                        tvGuiMo.setText(money + "亿");
                    }
                }
            }

            @Override
            public void priceCannel() {
            }
        }, 1);
        mbf1.show(getSupportFragmentManager(), "mbf");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_quick_match_add_2_style_ll1:
                styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleTv.setText(getString(R.string.acquisition));
                stylelll.setVisibility(View.VISIBLE);
                styleLl.setVisibility(View.GONE);
                break;
            case R.id.activity_quick_match_add_2_style_ll2:
                styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleTv.setText(getString(R.string.sell));
                stylelll.setVisibility(View.VISIBLE);
                styleLl.setVisibility(View.GONE);
                break;
            case R.id.activity_quick_match_add_2_style_ll3:
                styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleTv.setText(getString(R.string.investment));
                stylelll.setVisibility(View.VISIBLE);
                styleLl.setVisibility(View.GONE);
                break;
            case R.id.activity_quick_match_add_2_style_ll4:
                styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
                styleTv.setText(getString(R.string.financing));
                stylelll.setVisibility(View.VISIBLE);
                styleLl.setVisibility(View.GONE);
                break;
        }
    }

    private ArrayList<FluidLayoutBean> initListPlace() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < placeBeanList.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = placeBeanList.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = placeBeanList.get(i).name;
            } else {
                b.index = code;
            }
            b.text = placeBeanList.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutPlace(ArrayList<FluidLayoutBean> list) {
        flPlace.removeAllViews();
        flPlace.setGravity(Gravity.TOP);
        for (int i = 0; i < list.size(); i++) {

            final FluidLayoutBean bean = list.get(i);

            TextView tv = new TextView(this);
            tv.setPadding(25, 0, 25, 0);
            tv.setText(bean.text);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setClickable(true);

            tv.setBackgroundResource(R.mipmap.bf_match_delete);
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < placeBeanList.size(); j++) {
                        if (placeBeanList.get(j).name.equals(bean.text)) {
                            placeBeanList.remove(j);
                        }
                    }
                    initFluidLayoutPlace(initListPlace());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListChanPin() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < chanpinList.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = chanpinList.get(i).name;
            b.text = chanpinList.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutChanPin(ArrayList<FluidLayoutBean> list) {
        flChanpin.removeAllViews();
        flChanpin.setGravity(Gravity.TOP);
        for (int i = 0; i < list.size(); i++) {

            final FluidLayoutBean bean = list.get(i);

            TextView tv = new TextView(this);
            tv.setPadding(25, 0, 25, 0);
            tv.setText(bean.text);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setClickable(true);

            tv.setBackgroundResource(R.mipmap.bf_match_delete);
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < chanpinList.size(); j++) {
                        if (chanpinList.get(j).name.equals(bean.text)) {
                            chanpinList.remove(j);
                        }
                    }
                    initFluidLayoutChanPin(initListChanPin());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flChanpin.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListHangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < hangyeBeanList.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = hangyeBeanList.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = hangyeBeanList.get(i).name.get(0);
            } else {
                b.index = code;
            }
            b.text = hangyeBeanList.get(i).name.get(0);
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutHangYe(ArrayList<FluidLayoutBean> list) {
        flHangye.removeAllViews();
        flHangye.setGravity(Gravity.TOP);
        for (int i = 0; i < list.size(); i++) {

            final FluidLayoutBean bean = list.get(i);

            TextView tv = new TextView(this);
            tv.setPadding(25, 0, 25, 0);
            tv.setText(bean.text);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setClickable(true);

            tv.setBackgroundResource(R.mipmap.bf_match_delete);
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < hangyeBeanList.size(); j++) {
                        if (hangyeBeanList.get(j).name.get(0).equals(bean.text)) {
                            hangyeBeanList.remove(j);
                        }
                    }
                    initFluidLayoutHangYe(initListHangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flHangye.addView(tv, params);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001) {
            switch (requestCode) {
                case 101:
                    CodeNameBean placeBean = (CodeNameBean) data.getSerializableExtra(getString(R.string.PlaceBean));
                    if (!placeBeanList.contains(placeBean)) {
                        placeLine.setBackgroundColor(ContextCompat.getColor(QuickMatchAddActivity.this, R.color.color_999999));
                        placeBeanList.add(placeBean);
                        initFluidLayoutPlace(initListPlace());
                    }
                    break;
                case 102:
                    CodeTitleBean codeTitleBean = (CodeTitleBean) data.getSerializableExtra(getString(R.string.IndustryBean));
                    if (!hangyeBeanList.contains(codeTitleBean)) {
                        hangyeLine.setBackgroundColor(ContextCompat.getColor(QuickMatchAddActivity.this, R.color.color_999999));
                        hangyeBeanList.add(codeTitleBean);
                        initFluidLayoutHangYe(initListHangYe());
                    }
                    break;
                case 103:
                    CodeNameBean chanpin = (CodeNameBean) data.getSerializableExtra(getString(R.string.ProductBean));
                    if (!chanpinList.contains(chanpin)) {
                        chanpinList.add(chanpin);
                        initFluidLayoutChanPin(initListChanPin());
                    }
                    break;
            }
        }
    }

    private void initRequest(MatchCompanyRequest request) {

        request.intention = new ArrayList<>();

        //买：0101
//        卖：0102
//        投资：010301
//        融资：010302

        request.condition = new MatchCompanyRequestBean();//购买方自己的信息

        //对于所有数字类型的，对对方的需求是填区间，对自己的描述是填数字

        request.condition.ebitda = new ArrayList<>();//买方ebitda
        request.condition.industry_Classification = new ArrayList<>();//买方行业名称
        request.condition.location = new ArrayList<>();//购买方地理位置
        request.condition.market_Share = new MarketShareBean();
        request.condition.market_Share.marketShareQuantity = new ArrayList<>();//购买方市场占有率，市场份额
        request.condition.netProfit = new ArrayList<>();//买方净利润
        request.condition.otherKeyIndex = new ArrayList<>();//买方其他关键指标
        request.condition.price = new ArrayList<>(); //买方交易价格
        request.condition.product_productWords = new ArrayList<>(); //买方产品名称
        request.condition.profitMargin = new ArrayList<>();  //买方利润率
        request.condition.publisherInfo = new PublisherInfoBean();
        request.condition.publisherInfo.publisher_type = new ArrayList<>();//买方发布者信息
        request.condition.turnover = new ArrayList<>(); //买方营业额
        request.condition.round = new ArrayList<>();

        request.requirement = new MatchCompanyRequestBean();//卖方自己的信息

        //对于所有数字类型的，对对方的需求是填区间，对自己的描述是填数字

        request.requirement.ebitda = new ArrayList<>();//卖方ebitda
        request.requirement.industry_Classification = new ArrayList<>();//卖方行业名称
        request.requirement.location = new ArrayList<>();//卖方方地理位置
        request.requirement.market_Share = new MarketShareBean();
        request.requirement.market_Share.marketShareQuantity = new ArrayList<>();//卖方市场占有率，市场份额
        request.requirement.netProfit = new ArrayList<>();//卖方净利润
        request.requirement.otherKeyIndex = new ArrayList<>();//卖方其他关键指标
        request.requirement.price = new ArrayList<>(); //卖方交易价格
        request.requirement.product_productWords = new ArrayList<>(); //卖方产品名称
        request.requirement.profitMargin = new ArrayList<>();  //卖方利润率
        request.requirement.publisherInfo = new PublisherInfoBean();
        request.requirement.publisherInfo.publisher_type = new ArrayList<>();//卖方发布者信息
        request.requirement.turnover = new ArrayList<>(); //卖方营业额
        request.requirement.round = new ArrayList<>();

        request.condition.info_source = "";
        request.requirement.info_source = "";
        request.condition.entity_type = "";
    }

    private void matchCompany() {
        HttpHelper.matchCompany(request).subscribe(matchCompanyResponse -> {
            if (matchCompanyResponse != null && matchCompanyResponse.data != null && matchCompanyResponse.data.relationProject != null && matchCompanyResponse.data.relationProject.size() > 0) {

                Intent intent = new Intent();
                intent.putExtra(getString(R.string.TITLE), request.projectName);
                intent.putExtra(getString(R.string.result), matchCompanyResponse);
                setResult(110, intent);
                onBackPressed();
            } else {
                showToast(getString(R.string.there_is_no_result_please_wait));
            }

            LoadingFragment loadingFragment = findLoadingFragment();
            if (loadingFragment != null) {
                removeLoadingFragment();
            }

        }, throwable -> {
            LoadingFragment loadingFragment = findLoadingFragment();
            if (loadingFragment != null) {
                removeLoadingFragment();
            }

            if (throwable instanceof RequestErrorThrowable) {
                RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
                showToast(requestErrorThrowable.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
