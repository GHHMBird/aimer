package com.buyint.mergerbot.UIs.match.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.activity.MainActivity;
import com.buyint.mergerbot.Utility.StringListener;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.CodeNameBean;
import com.buyint.mergerbot.dto.CodeTitleBean;
import com.buyint.mergerbot.dto.MarketShareBean;
import com.buyint.mergerbot.dto.MatchCompanyRequest;
import com.buyint.mergerbot.dto.MatchCompanyRequestBean;
import com.buyint.mergerbot.dto.PublisherInfoBean;
import com.buyint.mergerbot.dto.UnitNumIndexBean;
import com.buyint.mergerbot.dto.UnitValueBean;
import com.buyint.mergerbot.dto.UnitValueModifierBean;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.view.FluidLayout;
import com.buyint.mergerbot.dto.FluidLayoutBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/2
 */

public class DeepMatchActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llCode, llTouRongRate, llCompanyYear, llCompanyNum, llTouRongTimes, llTouRongProduct, llTouRongHangye, llTouRongPlace, llBuyPlace, llBuyHangye, llBuyMRate, styleLl, llTouRong, llCome, llBuyType, llBiaoDiPlace, llBiaoDiMRate, llBiaoDiHangye, llBiaoDiProduct, llBuyProduct, styleLlShow, styleLl4, styleLl3, styleLl2, styleLl1, llDesc, llDescShow, llMaiFangShow, llMaiFang, llBiaoDi, llBiaoDiShow;
    private ImageView styleIv1, styleIv2, styleIv3, styleIv4;
    private boolean firstIn = true;
    private MatchCompanyRequest request = new MatchCompanyRequest();
    private TextView tvTitle, tv08, tv07, tv06, tv05, tvTouRongRate, tvCompanyYear, tvCompanyNum, llTouRongPrice2, llTouRongPrice, tv01, tv02, tv03, tvStyle, tvCome, tvBuyType, llBuyPrice, llBuyPrice2, llBuyOther, llBiaoDiMRateTv, llBiaoDiPrice, llBiaoDiPrice2, llBiaoDiOther, llBuyMRateTv;
    private FluidLayout flTouRongTimes, flTouRongProduct, flTouRongHangye, flTouRongPlace, flBiaoDiPlace, flBiaoDiHangye, flBiaoDiProduct, flBuyPlace, flBuyHangye, flBuyProduct;
    private EditText etPs, etTitle;
    private View lineView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_match);

        findViewById(R.id.activity_deep_match_next).setOnClickListener(this);

        tvTitle = findViewById(R.id.activity_deep_match_tv);

        llTouRong = findViewById(R.id.activity_deep_match_tourong_ll);


        etTitle = findViewById(R.id.activity_deep_match_title);
        etPs = findViewById(R.id.activity_deep_match_ps);

        styleLl1 = findViewById(R.id.activity_deep_match_style_ll1);
        styleLl2 = findViewById(R.id.activity_deep_match_style_ll2);
        styleLl3 = findViewById(R.id.activity_deep_match_style_ll3);
        styleLl4 = findViewById(R.id.activity_deep_match_style_ll4);
        styleLlShow = findViewById(R.id.activity_deep_match_style_ll_show);
        styleIv1 = findViewById(R.id.activity_deep_match_style_iv1);
        styleIv2 = findViewById(R.id.activity_deep_match_style_iv2);
        styleIv3 = findViewById(R.id.activity_deep_match_style_iv3);
        styleIv4 = findViewById(R.id.activity_deep_match_style_iv4);

        styleLl = findViewById(R.id.activity_deep_match_style_ll);
        tvStyle = findViewById(R.id.activity_deep_match_tv_style);

        llDesc = findViewById(R.id.activity_deep_match_ll_desc);
        llDescShow = findViewById(R.id.activity_deep_match_ll_desc_show);

        llBiaoDi = findViewById(R.id.activity_deep_match_biaodi_ll);
        llBiaoDiShow = findViewById(R.id.activity_deep_match_biaodi_ll_show);

        llMaiFang = findViewById(R.id.activity_deep_match_maifang_ll);
        llMaiFangShow = findViewById(R.id.activity_deep_match_maifang_ll_show);

        llBiaoDiPlace = findViewById(R.id.activity_deep_match_biaodi_place);
        flBiaoDiPlace = findViewById(R.id.activity_deep_match_biaodi_place_fl);
        llBiaoDiHangye = findViewById(R.id.activity_deep_match_biaodi_hangye);
        flBiaoDiHangye = findViewById(R.id.activity_deep_match_biaodi_hangye_fl);
        llBiaoDiProduct = findViewById(R.id.activity_deep_match_biaodi_product);
        flBiaoDiProduct = findViewById(R.id.activity_deep_match_biaodi_product_fl);

        llBiaoDiMRate = findViewById(R.id.activity_deep_match_biaodi_mark_rate);
        llBiaoDiMRateTv = findViewById(R.id.activity_deep_match_biaodi_mark_rate_tv);

        llBiaoDiPrice = findViewById(R.id.activity_deep_match_biaodi_price_ll);
        llBiaoDiPrice2 = findViewById(R.id.activity_deep_match_biaodi_price2_ll);
        llBiaoDiOther = findViewById(R.id.activity_deep_match_biaodi_other_ll);

        llBuyPlace = findViewById(R.id.activity_deep_match_buy_place);
        flBuyPlace = findViewById(R.id.activity_deep_match_buy_place_fl);
        llBuyHangye = findViewById(R.id.activity_deep_match_buy_hangye);
        flBuyHangye = findViewById(R.id.activity_deep_match_buy_hangye_fl);
        llBuyProduct = findViewById(R.id.activity_deep_match_buy_product);
        flBuyProduct = findViewById(R.id.activity_deep_match_buy_product_fl);

        llBuyMRate = findViewById(R.id.activity_deep_match_buy_mark_rate);
        llBuyMRateTv = findViewById(R.id.activity_deep_match_buy_mark_rate_tv);

        llBuyPrice = findViewById(R.id.activity_deep_match_buy_price_ll);
        llBuyPrice2 = findViewById(R.id.activity_deep_match_buy_price2_ll);
        llBuyOther = findViewById(R.id.activity_deep_match_buy_other_ll);

        llCome = findViewById(R.id.activity_deep_match_come_ll);
        tvCome = findViewById(R.id.activity_deep_match_come_tv);

        llBuyType = findViewById(R.id.activity_deep_match_buy_type_ll);
        tvBuyType = findViewById(R.id.activity_deep_match_buy_type_tv);

        llTouRongPlace = findViewById(R.id.activity_deep_match_tourong_place_ll);
        flTouRongPlace = findViewById(R.id.activity_deep_match_tourong_place_ll_show);
        llTouRongHangye = findViewById(R.id.activity_deep_match_tourong_hangye_ll);
        flTouRongHangye = findViewById(R.id.activity_deep_match_tourong_hangye_ll_show);
        llTouRongProduct = findViewById(R.id.activity_deep_match_tourong_product_ll);
        flTouRongProduct = findViewById(R.id.activity_deep_match_tourong_product_ll_show);

        llTouRongRate = findViewById(R.id.activity_deep_match_tourong_rate_ll);
        tvTouRongRate = findViewById(R.id.activity_deep_match_tourong_rate_tv);

        llTouRongTimes = findViewById(R.id.activity_deep_match_tourong_times);
        flTouRongTimes = findViewById(R.id.activity_deep_match_tourong_times_show);

        llTouRongPrice = findViewById(R.id.activity_deep_match_tourong_price);
        llTouRongPrice2 = findViewById(R.id.activity_deep_match_tourong_price2);

        llCompanyNum = findViewById(R.id.activity_deep_match_tourong_company_num);
        tvCompanyNum = findViewById(R.id.activity_deep_match_tourong_company_tv);
        llCompanyYear = findViewById(R.id.activity_deep_match_tourong_company_year);
        tvCompanyYear = findViewById(R.id.activity_deep_match_tourong_company_year_tv);

        scrollView = findViewById(R.id.activity_deep_match_scroll);

        llCode = findViewById(R.id.activity_deep_match_code_ll);
        lineView = findViewById(R.id.activity_deep_match_line);

        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());

        llCome.setOnClickListener(this);
        llCompanyYear.setOnClickListener(this);
        llTouRongPrice.setOnClickListener(this);
        styleLl.setOnClickListener(this);
        llTouRongRate.setOnClickListener(this);
        llTouRongPrice2.setOnClickListener(this);
        llCompanyNum.setOnClickListener(this);
        llBiaoDiMRate.setOnClickListener(this);
        llTouRongTimes.setOnClickListener(this);
        llBuyMRate.setOnClickListener(this);
        styleLl1.setOnClickListener(this);
        styleLl2.setOnClickListener(this);
        styleLl3.setOnClickListener(this);
        llTouRongHangye.setOnClickListener(this);
        llBuyOther.setOnClickListener(this);
        llBuyPrice2.setOnClickListener(this);
        styleLl4.setOnClickListener(this);
        llBiaoDiOther.setOnClickListener(this);
        llTouRongProduct.setOnClickListener(this);
        llBuyProduct.setOnClickListener(this);
        llBuyPrice.setOnClickListener(this);
        llBuyHangye.setOnClickListener(this);
        llDesc.setOnClickListener(this);
        llBiaoDiPrice.setOnClickListener(this);
        llBuyPlace.setOnClickListener(this);
        llBiaoDiPrice2.setOnClickListener(this);
        llBiaoDi.setOnClickListener(this);
        llMaiFang.setOnClickListener(this);
        llTouRongPlace.setOnClickListener(this);
        llBiaoDiPlace.setOnClickListener(this);
        llBiaoDiHangye.setOnClickListener(this);
        llBiaoDiProduct.setOnClickListener(this);

        tv01 = findViewById(R.id.activity_deep_match_tv1);
        tv02 = findViewById(R.id.activity_deep_match_tv2);
        tv03 = findViewById(R.id.activity_deep_match_tv3);
        tv05 = findViewById(R.id.activity_deep_match_tv5);
        tv06 = findViewById(R.id.activity_deep_match_tv6);
        tv07 = findViewById(R.id.activity_deep_match_tv7);
        tv08 = findViewById(R.id.activity_deep_match_tv8);

        initRequest();
    }

    private void initRequest() {

        request.intention = new ArrayList<>();
        request.matchType = new ArrayList<>();

        //购买意图或者出售意图

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
        request.requirement.entity_type = "";
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.activity_deep_match_next:

                if (request.intention.size() == 0) {
                    showToast(getString(R.string.please_choose_project_type));
                    return;
                }

                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    if (TextUtils.isEmpty(etTitle.getText().toString())) {
                        tvTitle.setText(getString(R.string.please_write_title));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_title));
                        return;
                    }
                    if (request.requirement.location.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_place));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_place));
                        return;
                    }
                    if (request.requirement.industry_Classification.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_industry));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_industry));
                        return;
                    }
                    if (request.requirement.product_productWords.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_product));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_product));
                        return;
                    }

                    request.description = etPs.getText().toString();
                    request.projectName = etTitle.getText().toString();
                    showLoadingFragment(R.id.activity_deep_match_fl);
                    matchCompany();
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    if (TextUtils.isEmpty(etTitle.getText().toString())) {
                        tvTitle.setText(getString(R.string.please_write_title));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_title));
                        return;
                    }
                    if (request.condition.location.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_place));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_place));
                        return;
                    }
                    if (request.condition.industry_Classification.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_industry));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_industry));
                        return;
                    }
                    if (request.condition.product_productWords.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_product));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_product));
                        return;
                    }

                    request.description = etPs.getText().toString();
                    request.projectName = etTitle.getText().toString();
                    showLoadingFragment(R.id.activity_deep_match_fl);
                    matchCompany();
                } else if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    if (TextUtils.isEmpty(etTitle.getText().toString())) {
                        tvTitle.setText(getString(R.string.please_write_title));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_title));
                        return;
                    }
                    if (request.requirement.location.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_place));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_place));
                        return;
                    }
                    if (request.requirement.industry_Classification.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_industry));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_industry));
                        return;
                    }
                    if (request.requirement.price.size() == 0) {
                        tvTitle.setText(getString(R.string.please_write_investment_price));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_investment_price));
                        return;
                    }
                    if (request.requirement.round.size() == 0) {
                        tvTitle.setText(getString(R.string.please_select_investment_round));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_select_investment_round));
                        return;
                    }
                    request.description = etPs.getText().toString();
                    request.projectName = etTitle.getText().toString();
                    showLoadingFragment(R.id.activity_deep_match_fl);
                    matchCompany();
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    if (TextUtils.isEmpty(etTitle.getText().toString())) {
                        tvTitle.setText(getString(R.string.please_write_title));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_title));
                        return;
                    }
                    if (request.condition.location.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_place));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_place));
                        return;
                    }
                    if (request.condition.industry_Classification.size() == 0) {
                        tvTitle.setText(getString(R.string.please_choose_industry));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_choose_industry));
                        return;
                    }
                    if (request.condition.price.size() == 0) {
                        tvTitle.setText(getString(R.string.please_write_investment_price));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_write_investment_price));
                        return;
                    }
                    if (request.condition.round.size() == 0) {
                        tvTitle.setText(getString(R.string.please_select_investment_round));
                        scrollView.scrollTo(0, 0);
                        showToast(getString(R.string.please_select_investment_round));
                        return;
                    }
                    request.description = etPs.getText().toString();
                    request.projectName = etTitle.getText().toString();
                    showLoadingFragment(R.id.activity_deep_match_fl);
                    matchCompany();
                }

                break;

            case R.id.activity_deep_match_style_ll://项目类型
                styleLl.setVisibility(View.GONE);
                styleLlShow.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_deep_match_ll_desc://项目概述
                if (llDescShow.getVisibility() == View.VISIBLE) {
                    llDescShow.setVisibility(View.GONE);
                } else {
                    llDescShow.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_deep_match_biaodi_ll:
                if (llBiaoDiShow.getVisibility() == View.VISIBLE) {
                    llBiaoDiShow.setVisibility(View.GONE);
                } else {
                    llBiaoDiShow.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_deep_match_maifang_ll:
                if (llMaiFangShow.getVisibility() == View.VISIBLE) {
                    llMaiFangShow.setVisibility(View.GONE);
                } else {
                    llMaiFangShow.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.activity_deep_match_style_ll1:
                setTypeChange();
                styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                tvStyle.setText(getString(R.string.buy_project));
                set0101Text();
                request.intention.add(getString(R.string.TYPE_0101));

                llBiaoDi.setVisibility(View.VISIBLE);
                llMaiFang.setVisibility(View.VISIBLE);
                llTouRong.setVisibility(View.GONE);
                lineView.setVisibility(View.VISIBLE);

                break;
            case R.id.activity_deep_match_style_ll2:
                setTypeChange();
                styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                tvStyle.setText(getString(R.string.sell_project));
                set0102Text();
                request.intention.add(getString(R.string.TYPE_0102));

                llBiaoDi.setVisibility(View.VISIBLE);
                llMaiFang.setVisibility(View.VISIBLE);
                llTouRong.setVisibility(View.GONE);
                lineView.setVisibility(View.VISIBLE);

                break;
            case R.id.activity_deep_match_style_ll3:
                setTypeChange();
                styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                tvStyle.setText(getString(R.string.investment_project));
                set010301Text();
                request.intention.add(getString(R.string.TYPE_010301));

                llBiaoDi.setVisibility(View.GONE);
                llMaiFang.setVisibility(View.GONE);
                llBiaoDiShow.setVisibility(View.GONE);
                llMaiFangShow.setVisibility(View.GONE);
                llTouRong.setVisibility(View.VISIBLE);
                lineView.setVisibility(View.GONE);

                break;
            case R.id.activity_deep_match_style_ll4:
                setTypeChange();
                styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black_shi));
                tvStyle.setText(getString(R.string.finance_project));
                set010302Text();
                request.intention.add(getString(R.string.TYPE_010302));

                llBiaoDi.setVisibility(View.GONE);
                llMaiFang.setVisibility(View.GONE);
                llBiaoDiShow.setVisibility(View.GONE);
                llMaiFangShow.setVisibility(View.GONE);
                llTouRong.setVisibility(View.VISIBLE);
                lineView.setVisibility(View.GONE);

                break;


            case R.id.activity_deep_match_biaodi_place:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 10);
                intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_position_of_the_label_company));
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10001);
                break;
            case R.id.activity_deep_match_buy_place:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 10);
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_position_of_your_company));
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_position_of_buyer_company));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10007);
                break;
            case R.id.activity_deep_match_tourong_place_ll:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 10);
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_location_of_the_company_you_want_invest_in));
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_position_of_your_company));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 11001);
                break;
            case R.id.activity_deep_match_biaodi_hangye:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 11);
                intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_label_company_in_the_industry));
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10002);
                break;
            case R.id.activity_deep_match_buy_hangye:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 11);
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_your_company_in_the_industry));
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_buyer_company_in_the_industry));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10008);
                break;
            case R.id.activity_deep_match_tourong_hangye_ll:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 11);
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_industry_where_you_want_to_invest));
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_your_company_in_the_industry));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 12008);
                break;
            case R.id.activity_deep_match_biaodi_product:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 12);
                intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_main_product_of_the_company));
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10003);
                break;
            case R.id.activity_deep_match_buy_product:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 12);
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_main_product_of_your_company));
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_main_product_of_buyer_company));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 10009);
                break;
            case R.id.activity_deep_match_tourong_product_ll:
                intent.setClass(DeepMatchActivity.this, QuickMatchActivity.class);
                intent.putExtra(getString(R.string.TYPE), 12);
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_main_product_of_the_company_you_want_to_invest_in));
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.TITLE), getString(R.string.please_enter_the_main_product_of_your_company));
                }
                intent.putExtra(getString(R.string.NAME), tvStyle.getText().toString().trim().replace(getString(R.string.project), ""));
                startActivityForResult(intent, 13009);
                break;
            case R.id.activity_deep_match_biaodi_mark_rate://市场占有率
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    list.add(i + "%");
                }
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_market_share), list, string -> {
                    llBiaoDiMRateTv.setText(string);
                    String replace = string.replace("%", "");
                    double marketShareQuantity = Double.parseDouble(replace) / 100;
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.requirement.market_Share.marketShareQuantity.clear();
                        request.requirement.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.condition.market_Share.marketShareQuantity.clear();
                        request.condition.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                    }
                });
                break;
            case R.id.activity_deep_match_buy_mark_rate://市场占有率
                ArrayList<String> list2 = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    list2.add(i + "%");
                }
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_market_share), list2, string -> {
                    llBuyMRateTv.setText(string);
                    String replace = string.replace("%", "");
                    double marketShareQuantity = Double.parseDouble(replace) / 100;
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.condition.market_Share.marketShareQuantity.clear();
                        request.condition.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.requirement.market_Share.marketShareQuantity.clear();
                        request.requirement.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                    }
                });
                break;
            case R.id.activity_deep_match_biaodi_price_ll://交易价格
                intent.setClass(DeepMatchActivity.this, PriceActivity.class);
                intent.putExtra(getString(R.string.NAME), getString(R.string.ideal_price_range));
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.price);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.price);
                }
                startActivityForResult(intent, 10004);
                break;
            case R.id.activity_deep_match_buy_price_ll://交易价格
                intent.setClass(DeepMatchActivity.this, PriceActivity.class);
                intent.putExtra(getString(R.string.NAME), getString(R.string.ideal_price_range));
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.price);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.price);
                }
                startActivityForResult(intent, 10010);
                break;
            case R.id.activity_deep_match_tourong_price://投资或者融资价格
                intent.setClass(DeepMatchActivity.this, PriceActivity.class);
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.price);
                    intent.putExtra(getString(R.string.NAME), getString(R.string.propose_investment_amount));
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.price);
                    intent.putExtra(getString(R.string.NAME), getString(R.string.finance_amount));
                }
                startActivityForResult(intent, 14010);
                break;
            case R.id.activity_deep_match_biaodi_price2_ll://
                intent.setClass(DeepMatchActivity.this, Price4Activity.class);
                intent.putExtra(getString(R.string.NAME), getString(R.string.financial_indicators_requirements));
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.EBITDA), request.requirement.ebitda);
                    intent.putExtra(getString(R.string.TURNOVER), request.requirement.turnover);
                    intent.putExtra(getString(R.string.NET_PROFIT), request.requirement.netProfit);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.EBITDA), request.condition.ebitda);
                    intent.putExtra(getString(R.string.TURNOVER), request.condition.turnover);
                    intent.putExtra(getString(R.string.NET_PROFIT), request.condition.netProfit);
                }
                startActivityForResult(intent, 10005);
                break;
            case R.id.activity_deep_match_buy_price2_ll://
                intent.setClass(DeepMatchActivity.this, Price4Activity.class);
                intent.putExtra(getString(R.string.NAME), getString(R.string.financial_indicators_requirements));
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.EBITDA), request.condition.ebitda);
                    intent.putExtra(getString(R.string.TURNOVER), request.condition.turnover);
                    intent.putExtra(getString(R.string.NET_PROFIT), request.condition.netProfit);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.EBITDA), request.requirement.ebitda);
                    intent.putExtra(getString(R.string.TURNOVER), request.requirement.turnover);
                    intent.putExtra(getString(R.string.NET_PROFIT), request.requirement.netProfit);
                }
                startActivityForResult(intent, 10011);
                break;
            case R.id.activity_deep_match_tourong_price2://
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.setClass(DeepMatchActivity.this, InvestmentPrice4Activity.class);
                    intent.putExtra(getString(R.string.NAME), getString(R.string.financial_indicators_requirements));
                    if (request.requirement.isGain) {
                        intent.putExtra(getString(R.string.ISGAIN), request.requirement.isGain);
                        intent.putExtra(getString(R.string.EBITDA), request.requirement.ebitda);
                        intent.putExtra(getString(R.string.TURNOVER), request.requirement.turnover);
                        intent.putExtra(getString(R.string.NET_PROFIT), request.requirement.netProfit);
                        intent.putExtra(getString(R.string.CASH_FLOW), request.requirement.cashFlow);
                    }
                    startActivityForResult(intent, 2001);
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.setClass(DeepMatchActivity.this, FinancePrice4Activity.class);
                    intent.putExtra(getString(R.string.NAME), getString(R.string.financial_indicators_requirements));
                    if (request.condition.isGain) {
                        intent.putExtra(getString(R.string.ISGAIN), request.condition.isGain);
                        intent.putExtra(getString(R.string.EBITDA), request.condition.ebitda);
                        intent.putExtra(getString(R.string.TURNOVER), request.condition.turnover);
                        intent.putExtra(getString(R.string.NET_PROFIT), request.condition.netProfit);
                        intent.putExtra(getString(R.string.CASH_FLOW), request.condition.cashFlow);
                    }
                    startActivityForResult(intent, 2002);
                }
                break;
            case R.id.activity_deep_match_biaodi_other_ll://其他价格区间
                intent.setClass(DeepMatchActivity.this, OtherTargetActivity.class);
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.otherKeyIndex);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.otherKeyIndex);
                }
                startActivityForResult(intent, 10006);
                break;
            case R.id.activity_deep_match_buy_other_ll://其他价格区间
                intent.setClass(DeepMatchActivity.this, OtherTargetActivity.class);
                if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.otherKeyIndex);
                } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.otherKeyIndex);
                }
                startActivityForResult(intent, 10012);
                break;

            case R.id.activity_deep_match_come_ll://项目需求来源
                ArrayList<String> lists = new ArrayList<>();
                lists.add(getString(R.string.one_hand));
                lists.add(getString(R.string.two_hand));
                lists.add(getString(R.string.unlimited));
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_select_the_source_of_the_project), lists, new StringListener() {
                    @Override
                    public void getStrings(String string) {
                        if (getString(R.string.unlimited).equals(string)) {
                            request.condition.info_source = "";
                            tvCome.setText(string);
                            return;
                        }
                        request.condition.info_source = string;
                        tvCome.setText(string);
                    }
                });
                break;
            case R.id.activity_deep_match_buy_type_ll://
                ArrayList<String> listss = new ArrayList<>();
                listss.add(getString(R.string.listed_company));
                listss.add(getString(R.string.non_listed_company));
                listss.add(getString(R.string.m_a_fund));
                listss.add(getString(R.string.unlimited));
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_buyer_type), listss, new StringListener() {
                    @Override
                    public void getStrings(String string) {
                        if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                            if (getString(R.string.unlimited).equals(string)) {
                                request.condition.entity_type = "";
                                tvBuyType.setText(string);
                                return;
                            }
                            request.condition.entity_type = string;
                            tvBuyType.setText(string);
                        } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                            if (getString(R.string.unlimited).equals(string)) {
                                request.requirement.entity_type = "";
                                tvBuyType.setText(string);
                                return;
                            }
                            request.requirement.entity_type = string;
                            tvBuyType.setText(string);
                        }
                    }
                });
                break;

            case R.id.activity_deep_match_tourong_times:
                intent = new Intent(this, ChooseFinanceTimesActivity.class);
                if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.requirement.round);
                } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                    intent.putExtra(getString(R.string.LIST), request.condition.round);
                }
                startActivityForResult(intent, 12305);
                break;
            case R.id.activity_deep_match_tourong_company_num:
                ArrayList<String> listw = new ArrayList<>();
                listw.add(getString(R.string.zero_to_twenty));
                listw.add(getString(R.string.twenty_to_fifty));
                listw.add(getString(R.string.fifty_to_hundred));
                listw.add(getString(R.string.hundred_to_five_hundred));
                listw.add(getString(R.string.five_hundred_to_thousand));
                listw.add(getString(R.string.thousand_to_five_thousand));
                listw.add(getString(R.string.more_than_five_thousand));
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_the_number_of_company), listw, new StringListener() {
                    @Override
                    public void getStrings(String string) {
                        tvCompanyNum.setText(string);
                        if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                            request.requirement.num_employee = string;
                        } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                            request.condition.num_employee = string;
                        }
                    }
                });
                break;
            case R.id.activity_deep_match_tourong_company_year:
                ArrayList<String> li = new ArrayList<>();
                for (int i = 2018; i > 1900; i--) {
                    li.add(i + "");
                }
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_the_company_to_set_up_a_year), li, new StringListener() {
                    @Override
                    public void getStrings(String string) {
                        tvCompanyYear.setText(string);
                        if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                            request.requirement.year_established = string;
                        } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                            request.condition.year_established = string;
                        }
                    }
                });
                break;
            case R.id.activity_deep_match_tourong_rate_ll:
                ArrayList<String> listt = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    listt.add(i + "%");
                }
                ViewHelper.showWheelViewDialog(DeepMatchActivity.this, getString(R.string.please_choose_market_share), listt, new StringListener() {
                    @Override
                    public void getStrings(String string) {
                        tvTouRongRate.setText(string);
                        String replace = string.replace("%", "");
                        double marketShareQuantity = Double.parseDouble(replace) / 100;
                        if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                            request.requirement.market_Share.marketShareQuantity.clear();
                            request.requirement.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                        } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                            request.condition.market_Share.marketShareQuantity.clear();
                            request.condition.market_Share.marketShareQuantity.add("" + marketShareQuantity);
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001) {
            switch (requestCode) {
                case 10001:
                    CodeNameBean placeBean = (CodeNameBean) data.getSerializableExtra(getString(R.string.PlaceBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.requirement.location.contains(placeBean)) {
                            request.requirement.location.add(placeBean);
                            initFluidLayoutBiaoDi0101Place(initListBiaoDi0101Place());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.condition.location.contains(placeBean)) {
                            request.condition.location.add(placeBean);
                            initFluidLayoutBiaoDi0102Place(initListBiaoDi0102Place());
                        }
                    }
                    break;
                case 10007:
                    CodeNameBean placeBean2 = (CodeNameBean) data.getSerializableExtra(getString(R.string.PlaceBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.condition.location.contains(placeBean2)) {
                            request.condition.location.add(placeBean2);
                            initFluidLayoutBuy0101Place(initListBuy0101Place());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.requirement.location.contains(placeBean2)) {
                            request.requirement.location.add(placeBean2);
                            initFluidLayoutBuy0102Place(initListBuy0102Place());
                        }
                    }
                    break;
                case 11001:
                    CodeNameBean placeBean4 = (CodeNameBean) data.getSerializableExtra(getString(R.string.PlaceBean));
                    if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                        if (!request.requirement.location.contains(placeBean4)) {
                            request.requirement.location.add(placeBean4);
                            initFluidLayout010301Place(initList010301Place());
                        }
                    } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                        if (!request.condition.location.contains(placeBean4)) {
                            request.condition.location.add(placeBean4);
                            initFluidLayout010302Place(initList010302Place());
                        }
                    }
                    break;
                case 10002:
                    CodeTitleBean codeTitleBean = (CodeTitleBean) data.getSerializableExtra(getString(R.string.IndustryBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.requirement.industry_Classification.contains(codeTitleBean)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean.name.get(0);
                            c1.code = codeTitleBean.code;
                            request.requirement.industry_Classification.add(c1);
                            initFluidLayoutBiaoDi0101HangYe(initListBiaoDi0101HangYe());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.condition.industry_Classification.contains(codeTitleBean)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean.name.get(0);
                            c1.code = codeTitleBean.code;
                            request.condition.industry_Classification.add(c1);
                            initFluidLayoutBiaoDi0102HangYe(initListBiaoDi0102HangYe());
                        }
                    }
                    break;
                case 10008:
                    CodeTitleBean codeTitleBean2 = (CodeTitleBean) data.getSerializableExtra(getString(R.string.IndustryBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.condition.industry_Classification.contains(codeTitleBean2)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean2.name.get(0);
                            c1.code = codeTitleBean2.code;
                            request.condition.industry_Classification.add(c1);
                            initFluidLayoutBuy0101HangYe(initListBuy0101HangYe());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.requirement.industry_Classification.contains(codeTitleBean2)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean2.name.get(0);
                            c1.code = codeTitleBean2.code;
                            request.requirement.industry_Classification.add(c1);
                            initFluidLayoutBuy0102HangYe(initListBuy0102HangYe());
                        }
                    }
                    break;
                case 12008:
                    CodeTitleBean codeTitleBean22 = (CodeTitleBean) data.getSerializableExtra(getString(R.string.IndustryBean));
                    if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                        if (!request.requirement.industry_Classification.contains(codeTitleBean22)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean22.name.get(0);
                            c1.code = codeTitleBean22.code;
                            request.requirement.industry_Classification.add(c1);
                            initFluidLayout010301HangYe(initList010301HangYe());
                        }
                    } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                        if (!request.condition.industry_Classification.contains(codeTitleBean22)) {
                            CodeNameBean c1 = new CodeNameBean();
                            c1.name = codeTitleBean22.name.get(0);
                            c1.code = codeTitleBean22.code;
                            request.condition.industry_Classification.add(c1);
                            initFluidLayout010302HangYe(initList010302HangYe());
                        }
                    }
                    break;
                case 10003:
                    CodeNameBean chanpin = (CodeNameBean) data.getSerializableExtra(getString(R.string.ProductBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.requirement.product_productWords.contains(chanpin)) {
                            request.requirement.product_productWords.add(chanpin);
                            initFluidLayoutBiaoDi0101Product(initListBiaoDi0101Product());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.condition.product_productWords.contains(chanpin)) {
                            request.condition.product_productWords.add(chanpin);
                            initFluidLayoutBiaoDi0102Product(initListBiaoDi0102Product());
                        }
                    }
                    break;
                case 10009:
                    CodeNameBean chanpin2 = (CodeNameBean) data.getSerializableExtra(getString(R.string.ProductBean));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        if (!request.condition.product_productWords.contains(chanpin2)) {
                            request.condition.product_productWords.add(chanpin2);
                            initFluidLayoutBuy0101Product(initListBuy0101Product());
                        }
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        if (!request.requirement.product_productWords.contains(chanpin2)) {
                            request.requirement.product_productWords.add(chanpin2);
                            initFluidLayoutBuy0102Product(initListBuy0102Product());
                        }
                    }
                    break;
                case 13009:
                    CodeNameBean chanpin12 = (CodeNameBean) data.getSerializableExtra(getString(R.string.ProductBean));
                    if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                        if (!request.requirement.product_productWords.contains(chanpin12)) {
                            request.requirement.product_productWords.add(chanpin12);
                            initFluidLayout010301Product(initList010301Product());
                        }
                    } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                        if (!request.condition.product_productWords.contains(chanpin12)) {
                            request.condition.product_productWords.add(chanpin12);
                            initFluidLayout010302Product(initList010302Product());
                        }
                    }
                    break;
                case 10004:
                    UnitValueBean u1 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data1));
                    UnitValueBean u2 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data2));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.requirement.price.clear();
                        request.requirement.price.add(u1);
                        request.requirement.price.add(u2);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.condition.price.clear();
                        request.condition.price.add(u1);
                        request.condition.price.add(u2);
                    }
                    break;
                case 10010:
                    UnitValueBean u11 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data1));
                    UnitValueBean u21 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data2));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.condition.price.clear();
                        request.condition.price.add(u11);
                        request.condition.price.add(u21);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.requirement.price.clear();
                        request.requirement.price.add(u11);
                        request.requirement.price.add(u21);
                    }
                    break;
                case 14010:
                    UnitValueBean uu111 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data1));
                    UnitValueBean uu121 = (UnitValueBean) data.getSerializableExtra(getString(R.string.data2));
                    if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                        request.requirement.price.clear();
                        request.requirement.price.add(uu111);
                        request.requirement.price.add(uu121);
                    } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                        request.condition.price.clear();
                        request.condition.price.add(uu111);
                        request.condition.price.add(uu121);
                    }
                    break;
                case 10005:
                    UnitValueBean uu1 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta1));
                    UnitValueBean uu2 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta2));
                    UnitValueModifierBean uvm1 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn1));
                    UnitValueModifierBean uvm2 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn2));
                    UnitValueBean uv1 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit1));
                    UnitValueBean uv2 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit2));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.requirement.ebitda.clear();
                        request.requirement.ebitda.add(uu1);
                        request.requirement.ebitda.add(uu2);
                        request.requirement.turnover.clear();
                        request.requirement.turnover.add(uvm1);
                        request.requirement.turnover.add(uvm2);
                        request.requirement.netProfit.clear();
                        request.requirement.netProfit.add(uv1);
                        request.requirement.netProfit.add(uv2);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.condition.ebitda.clear();
                        request.condition.ebitda.add(uu1);
                        request.condition.ebitda.add(uu2);
                        request.condition.turnover.clear();
                        request.condition.turnover.add(uvm1);
                        request.condition.turnover.add(uvm2);
                        request.condition.netProfit.clear();
                        request.condition.netProfit.add(uv1);
                        request.condition.netProfit.add(uv2);
                    }
                    break;
                case 10011:
                    UnitValueBean uu11 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta1));
                    UnitValueBean uu21 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta2));
                    UnitValueModifierBean uvm11 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn1));
                    UnitValueModifierBean uvm21 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn2));
                    UnitValueBean uv11 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit1));
                    UnitValueBean uv21 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit2));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.condition.ebitda.clear();
                        request.condition.ebitda.add(uu11);
                        request.condition.ebitda.add(uu21);
                        request.condition.turnover.clear();
                        request.condition.turnover.add(uvm11);
                        request.condition.turnover.add(uvm21);
                        request.condition.netProfit.clear();
                        request.condition.netProfit.add(uv11);
                        request.condition.netProfit.add(uv21);
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.requirement.ebitda.clear();
                        request.requirement.ebitda.add(uu11);
                        request.requirement.ebitda.add(uu21);
                        request.requirement.turnover.clear();
                        request.requirement.turnover.add(uvm11);
                        request.requirement.turnover.add(uvm21);
                        request.requirement.netProfit.clear();
                        request.requirement.netProfit.add(uv11);
                        request.requirement.netProfit.add(uv21);
                    }
                    break;
                case 10006:
                    ArrayList<UnitNumIndexBean> list = (ArrayList<UnitNumIndexBean>) data.getSerializableExtra(getString(R.string.LIST));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.requirement.otherKeyIndex = list;
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.condition.otherKeyIndex = list;
                    }
                    break;
                case 10012:
                    ArrayList<UnitNumIndexBean> list2 = (ArrayList<UnitNumIndexBean>) data.getSerializableExtra(getString(R.string.LIST));
                    if (getString(R.string.TYPE_0101).equals(request.intention.get(0))) {
                        request.condition.otherKeyIndex = list2;
                    } else if (getString(R.string.TYPE_0102).equals(request.intention.get(0))) {
                        request.requirement.otherKeyIndex = list2;
                    }
                    break;
                case 12305:
                    ArrayList<String> lista = (ArrayList<String>) data.getSerializableExtra(getString(R.string.DATA));
                    if (getString(R.string.TYPE_010301).equals(request.intention.get(0))) {
                        request.requirement.round = lista;
                        initFluidLayout010301(initList010301());
                    } else if (getString(R.string.TYPE_010302).equals(request.intention.get(0))) {
                        request.condition.round = lista;
                        initFluidLayout010302(initList010302());
                    }
                    break;
                case 2001:
                    boolean isGain = data.getBooleanExtra(getString(R.string.ISGAIN), false);
                    if (isGain) {
                        UnitValueBean uu118 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta1));
                        UnitValueBean uu218 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta2));
                        UnitValueModifierBean uvm118 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn1));
                        UnitValueModifierBean uvm218 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn2));
                        UnitValueBean uv118 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit1));
                        UnitValueBean uv218 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit2));
                        UnitValueBean c1 = (UnitValueBean) data.getSerializableExtra(getString(R.string.cashflow1));
                        UnitValueBean c2 = (UnitValueBean) data.getSerializableExtra(getString(R.string.cashflow2));
                        request.requirement.ebitda.clear();
                        request.requirement.ebitda.add(uu118);
                        request.requirement.ebitda.add(uu218);
                        request.requirement.turnover.clear();
                        request.requirement.turnover.add(uvm118);
                        request.requirement.turnover.add(uvm218);
                        request.requirement.netProfit.clear();
                        request.requirement.netProfit.add(uv118);
                        request.requirement.netProfit.add(uv218);
                        request.requirement.cashFlow.clear();
                        request.requirement.cashFlow.add(c1);
                        request.requirement.cashFlow.add(c2);
                    }
                    request.requirement.isGain = isGain;
                    break;
                case 2002:
                    boolean isGain2 = data.getBooleanExtra(getString(R.string.ISGAIN), false);
                    if (isGain2) {
                        UnitValueBean uu218 = (UnitValueBean) data.getSerializableExtra(getString(R.string.ebidta2));
                        UnitValueModifierBean uvm218 = (UnitValueModifierBean) data.getSerializableExtra(getString(R.string.earn2));
                        UnitValueBean uv218 = (UnitValueBean) data.getSerializableExtra(getString(R.string.netprofit2));
                        UnitValueBean c2 = (UnitValueBean) data.getSerializableExtra(getString(R.string.cashflow1));
                        request.condition.ebitda.clear();
                        request.condition.ebitda.add(uu218);
                        request.condition.turnover.clear();
                        request.condition.turnover.add(uvm218);
                        request.condition.netProfit.clear();
                        request.condition.netProfit.add(uv218);
                        request.condition.cashFlow.clear();
                        request.condition.cashFlow.add(c2);
                    }
                    request.condition.isGain = isGain2;
                    break;
            }
        }
    }

    private void setTypeChange() {
        styleIv1.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        styleIv2.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        styleIv3.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        styleIv4.setBackground(ContextCompat.getDrawable(this, R.drawable.cicle_black));
        styleLl.setVisibility(View.VISIBLE);
        styleLlShow.setVisibility(View.GONE);
        if (firstIn) {
            llDescShow.setVisibility(View.VISIBLE);
            llCode.setVisibility(View.VISIBLE);
            firstIn = false;
        }
        initRequest();
        clearAllData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0101Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0101Place(ArrayList<FluidLayoutBean> list) {
        flBiaoDiPlace.removeAllViews();
        flBiaoDiPlace.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.location.size(); j++) {
                        if (request.requirement.location.get(j).name.equals(bean.text)) {
                            request.requirement.location.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0101Place(initListBiaoDi0101Place());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0102Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.condition.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0102Place(ArrayList<FluidLayoutBean> list) {
        flBiaoDiPlace.removeAllViews();
        flBiaoDiPlace.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.location.size(); j++) {
                        if (request.condition.location.get(j).name.equals(bean.text)) {
                            request.condition.location.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0102Place(initListBiaoDi0102Place());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0101HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0101HangYe(ArrayList<FluidLayoutBean> list) {
        flBiaoDiHangye.removeAllViews();
        flBiaoDiHangye.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.industry_Classification.size(); j++) {
                        if (request.requirement.industry_Classification.get(j).name.equals(bean.text)) {
                            request.requirement.industry_Classification.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0101HangYe(initListBiaoDi0101HangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiHangye.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0102HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.condition.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0102HangYe(ArrayList<FluidLayoutBean> list) {
        flBiaoDiHangye.removeAllViews();
        flBiaoDiHangye.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.industry_Classification.size(); j++) {
                        if (request.condition.industry_Classification.get(j).name.equals(bean.text)) {
                            request.condition.industry_Classification.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0102HangYe(initListBiaoDi0102HangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiHangye.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0101Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.requirement.product_productWords.get(i).name;
            b.text = request.requirement.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0101Product(ArrayList<FluidLayoutBean> list) {
        flBiaoDiProduct.removeAllViews();
        flBiaoDiProduct.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.product_productWords.size(); j++) {
                        if (request.requirement.product_productWords.get(j).name.equals(bean.text)) {
                            request.requirement.product_productWords.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0101Product(initListBiaoDi0101Product());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBiaoDi0102Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.condition.product_productWords.get(i).name;
            b.text = request.condition.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBiaoDi0102Product(ArrayList<FluidLayoutBean> list) {
        flBiaoDiProduct.removeAllViews();
        flBiaoDiProduct.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.product_productWords.size(); j++) {
                        if (request.condition.product_productWords.get(j).name.equals(bean.text)) {
                            request.condition.product_productWords.remove(j);
                        }
                    }
                    initFluidLayoutBiaoDi0102Product(initListBiaoDi0102Product());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBiaoDiProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBuy0101Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.condition.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0101Place(ArrayList<FluidLayoutBean> list) {
        flBuyPlace.removeAllViews();
        flBuyPlace.setGravity(Gravity.TOP);
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

            tv.setOnClickListener(v -> {
                for (int j = 0; j < request.condition.location.size(); j++) {
                    if (request.condition.location.get(j).name.equals(bean.text)) {
                        request.condition.location.remove(j);
                    }
                }
                initFluidLayoutBuy0101Place(initListBuy0101Place());
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBuy0102Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0102Place(ArrayList<FluidLayoutBean> list) {
        flBuyPlace.removeAllViews();
        flBuyPlace.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.location.size(); j++) {
                        if (request.requirement.location.get(j).name.equals(bean.text)) {
                            request.requirement.location.remove(j);
                        }
                    }
                    initFluidLayoutBuy0102Place(initListBuy0102Place());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBuy0101HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.condition.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0101HangYe(ArrayList<FluidLayoutBean> list) {
        flBuyHangye.removeAllViews();
        flBuyHangye.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.industry_Classification.size(); j++) {
                        if (request.condition.industry_Classification.get(j).name.equals(bean.text)) {
                            request.condition.industry_Classification.remove(j);
                        }
                    }
                    initFluidLayoutBuy0101HangYe(initListBuy0101HangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyHangye.addView(tv, params);
        }
    }


    private ArrayList<FluidLayoutBean> initListBuy0102HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0102HangYe(ArrayList<FluidLayoutBean> list) {
        flBuyHangye.removeAllViews();
        flBuyHangye.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.industry_Classification.size(); j++) {
                        if (request.requirement.industry_Classification.get(j).name.equals(bean.text)) {
                            request.requirement.industry_Classification.remove(j);
                        }
                    }
                    initFluidLayoutBuy0102HangYe(initListBuy0102HangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyHangye.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBuy0101Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.condition.product_productWords.get(i).name;
            b.text = request.condition.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0101Product(ArrayList<FluidLayoutBean> list) {
        flBuyProduct.removeAllViews();
        flBuyProduct.setGravity(Gravity.TOP);
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

            tv.setOnClickListener(v -> {
                for (int j = 0; j < request.condition.product_productWords.size(); j++) {
                    if (request.condition.product_productWords.get(j).name.equals(bean.text)) {
                        request.condition.product_productWords.remove(j);
                    }
                }
                initFluidLayoutBuy0101Product(initListBuy0101Product());
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initListBuy0102Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.requirement.product_productWords.get(i).name;
            b.text = request.requirement.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayoutBuy0102Product(ArrayList<FluidLayoutBean> list) {
        flBuyProduct.removeAllViews();
        flBuyProduct.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.product_productWords.size(); j++) {
                        if (request.requirement.product_productWords.get(j).name.equals(bean.text)) {
                            request.requirement.product_productWords.remove(j);
                        }
                    }
                    initFluidLayoutBuy0102Product(initListBuy0102Product());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flBuyProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010301Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010301Place(ArrayList<FluidLayoutBean> list) {
        flTouRongPlace.removeAllViews();
        flTouRongPlace.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.location.size(); j++) {
                        if (request.requirement.location.get(j).name.equals(bean.text)) {
                            request.requirement.location.remove(j);
                        }
                    }
                    initFluidLayout010301Place(initList010301Place());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010302Place() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.location.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.location.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.location.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.location.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010302Place(ArrayList<FluidLayoutBean> list) {
        flTouRongPlace.removeAllViews();
        flTouRongPlace.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.location.size(); j++) {
                        if (request.condition.location.get(j).name.equals(bean.text)) {
                            request.condition.location.remove(j);
                        }
                    }
                    initFluidLayout010302Place(initList010302Place());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongPlace.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010301HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.requirement.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.requirement.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.requirement.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010301HangYe(ArrayList<FluidLayoutBean> list) {
        flTouRongHangye.removeAllViews();
        flTouRongHangye.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.industry_Classification.size(); j++) {
                        if (request.requirement.industry_Classification.get(j).name.equals(bean.text)) {
                            request.requirement.industry_Classification.remove(j);
                        }
                    }
                    initFluidLayout010301HangYe(initList010301HangYe());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongHangye.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010302HangYe() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.industry_Classification.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String code = request.condition.industry_Classification.get(i).code;
            if (TextUtils.isEmpty(code)) {
                b.index = request.condition.industry_Classification.get(i).name;
            } else {
                b.index = code;
            }
            b.text = request.condition.industry_Classification.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010302HangYe(ArrayList<FluidLayoutBean> list) {
        flTouRongHangye.removeAllViews();
        flTouRongHangye.setGravity(Gravity.TOP);
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

            tv.setOnClickListener(v -> {
                for (int j = 0; j < request.condition.industry_Classification.size(); j++) {
                    if (request.condition.industry_Classification.get(j).name.equals(bean.text)) {
                        request.condition.industry_Classification.remove(j);
                    }
                }
                initFluidLayout010302HangYe(initList010302HangYe());
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongHangye.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010301Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.requirement.product_productWords.get(i).name;
            b.text = request.requirement.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010301Product(ArrayList<FluidLayoutBean> list) {
        flTouRongProduct.removeAllViews();
        flTouRongProduct.setGravity(Gravity.TOP);
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

            tv.setOnClickListener(v -> {
                for (int j = 0; j < request.requirement.product_productWords.size(); j++) {
                    if (request.requirement.product_productWords.get(j).name.equals(bean.text)) {
                        request.requirement.product_productWords.remove(j);
                    }
                }
                initFluidLayout010301Product(initList010301Product());
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010302Product() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.product_productWords.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            b.index = request.condition.product_productWords.get(i).name;
            b.text = request.condition.product_productWords.get(i).name;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010302Product(ArrayList<FluidLayoutBean> list) {
        flTouRongProduct.removeAllViews();
        flTouRongProduct.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.product_productWords.size(); j++) {
                        if (request.condition.product_productWords.get(j).name.equals(bean.text)) {
                            request.condition.product_productWords.remove(j);
                        }
                    }
                    initFluidLayout010302Product(initList010302Product());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongProduct.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010301() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.requirement.round.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String text = request.requirement.round.get(i);
            switch (text) {
                case "0101":
                    b.text = getString(R.string.seed);
                    break;
                case "0102":
                    b.text = getString(R.string.angel);
                    break;
                case "0103":
                    b.text = getString(R.string.informality);
                    break;
                case "0104":
                    b.text = getString(R.string.pre_a_cicle);
                    break;
                case "0105":
                    b.text = getString(R.string.pre_a);
                    break;
                case "0106":
                    b.text = getString(R.string.pre_a_plus);
                    break;
                case "0201":
                    b.text = getString(R.string.pre_b_cicle);
                    break;
                case "0202":
                    b.text = getString(R.string.pre_b);
                    break;
                case "0203":
                    b.text = getString(R.string.pre_b_plus);
                    break;
                case "0204":
                    b.text = getString(R.string.pre_c);
                    break;
                case "0205":
                    b.text = getString(R.string.pre_c_plus);
                    break;
                case "0301":
                    b.text = getString(R.string.pre_d);
                    break;
                case "0302":
                    b.text = getString(R.string.pre_e);
                    break;
                case "0303":
                    b.text = getString(R.string.pre_f);
                    break;
                case "0304":
                    b.text = getString(R.string.pre_ipo);
                    break;
                case "0305":
                    b.text = getString(R.string.pre_neeq);
                    break;
                case "0306":
                    b.text = getString(R.string.ipo_plus);
                    break;
            }
            b.index = text;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010301(ArrayList<FluidLayoutBean> list) {
        flTouRongTimes.removeAllViews();
        flTouRongTimes.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.requirement.round.size(); j++) {
                        if (request.requirement.round.get(j).equals(bean.index)) {
                            request.requirement.round.remove(j);
                        }
                    }
                    initFluidLayout010301(initList010301());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongTimes.addView(tv, params);
        }
    }

    private ArrayList<FluidLayoutBean> initList010302() {
        ArrayList<FluidLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < request.condition.round.size(); i++) {
            FluidLayoutBean b = new FluidLayoutBean();
            String text = request.condition.round.get(i);
            switch (text) {
                case "0101":
                    b.text = getString(R.string.seed);
                    break;
                case "0102":
                    b.text = getString(R.string.angel);
                    break;
                case "0103":
                    b.text = getString(R.string.informality);
                    break;
                case "0104":
                    b.text = getString(R.string.pre_a_cicle);
                    break;
                case "0105":
                    b.text = getString(R.string.pre_a);
                    break;
                case "0106":
                    b.text = getString(R.string.pre_a_plus);
                    break;
                case "0201":
                    b.text = getString(R.string.pre_b_cicle);
                    break;
                case "0202":
                    b.text = getString(R.string.pre_b);
                    break;
                case "0203":
                    b.text = getString(R.string.pre_b_plus);
                    break;
                case "0204":
                    b.text = getString(R.string.pre_c);
                    break;
                case "0205":
                    b.text = getString(R.string.pre_c_plus);
                    break;
                case "0301":
                    b.text = getString(R.string.pre_d);
                    break;
                case "0302":
                    b.text = getString(R.string.pre_e);
                    break;
                case "0303":
                    b.text = getString(R.string.pre_f);
                    break;
                case "0304":
                    b.text = getString(R.string.pre_ipo);
                    break;
                case "0305":
                    b.text = getString(R.string.pre_neeq);
                    break;
                case "0306":
                    b.text = getString(R.string.ipo_plus);
                    break;
            }
            b.index = text;
            list.add(b);
        }
        return list;
    }

    private void initFluidLayout010302(ArrayList<FluidLayoutBean> list) {
        flTouRongTimes.removeAllViews();
        flTouRongTimes.setGravity(Gravity.TOP);
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
                    for (int j = 0; j < request.condition.round.size(); j++) {
                        if (request.condition.round.get(j).equals(bean.index)) {
                            request.condition.round.remove(j);
                        }
                    }
                    initFluidLayout010302(initList010302());
                }
            });

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 30, 20);
            flTouRongTimes.addView(tv, params);
        }
    }

    public void set0101Text() {
        tvTitle.setText(getString(R.string.please_choose_project_type));
        tv01.setText(getString(R.string.the_requirement_for_the_subject_matter));
        tv02.setText(getString(R.string.buyer_information));
        tv03.setText(getString(R.string.location));
    }

    public void set0102Text() {
        tvTitle.setText(getString(R.string.please_choose_project_type));
        tv01.setText(getString(R.string.standard_information));
        tv02.setText(getString(R.string.demand_for_the_buyer));
        tv03.setText(getString(R.string.location_of_the_buyer));
    }

    public void set010301Text() {
        tvTitle.setText(getString(R.string.please_choose_project_type));
        tv05.setText(getString(R.string.investment_place));
        tv06.setText(getString(R.string.investment_industry));
        tv07.setText(getString(R.string.investment_product));
        tv08.setText(getString(R.string.investment_rounds));
        llTouRongPrice.setText(getString(R.string.propose_investment_amount));
    }

    public void set010302Text() {
        tvTitle.setText(getString(R.string.please_choose_project_type));
        tv05.setText(getString(R.string.location_in));
        tv06.setText(getString(R.string.industry_in));
        tv07.setText(getString(R.string.main_product));
        tv08.setText(getString(R.string.quasi_financing_rotation));
        llTouRongPrice.setText(getString(R.string.finance_amount));
    }

    public void clearAllData() {
        tvCome.setText("");
        tvBuyType.setText("");
        etTitle.setText("");
        etPs.setText("");
        flBiaoDiProduct.removeAllViews();
        flBiaoDiHangye.removeAllViews();
        flBiaoDiPlace.removeAllViews();
        flBuyProduct.removeAllViews();
        flBuyHangye.removeAllViews();
        flBuyPlace.removeAllViews();
        flTouRongTimes.removeAllViews();
        flTouRongHangye.removeAllViews();
        flTouRongPlace.removeAllViews();
        flTouRongProduct.removeAllViews();
        tvTouRongRate.setText("");
        tvCompanyNum.setText("");
        tvCompanyYear.setText("");

    }

    private void matchCompany() {
        request.projectCategory = 1;
        HttpHelper.matchCompany(request).subscribe(matchCompanyResponse -> {
            if (matchCompanyResponse != null && matchCompanyResponse.data != null && matchCompanyResponse.data.relationProject != null && matchCompanyResponse.data.relationProject.size() > 0) {
                Intent intent = new Intent(DeepMatchActivity.this, MainActivity.class);
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1));
                intent.putExtra(getString(R.string.DATA), matchCompanyResponse);
                startActivity(intent);
                onBackPressed();
            } else {
                showToast(getString(R.string.there_is_no_result_please_wait));
            }
            removeLoadingFragment();
        }, throwable -> {
            removeLoadingFragment();
            showThrowable(throwable);
        });
    }

    @Override
    protected void onResume() {

        //百度埋点
        StatService.onResume(this);

        //页面时长埋点
        StatService.onPageStart(this, getString(R.string.matchdetailactivity));

        super.onResume();
    }

    @Override
    protected void onPause() {

        //百度埋点
        StatService.onResume(this);

        //页面时长埋点
        StatService.onPageEnd(this, getString(R.string.matchdetailactivity));

        super.onPause();
    }
}
