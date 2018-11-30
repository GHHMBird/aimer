package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchCompanyRequestBean implements Serializable {

    @Expose
    @SerializedName("ebitda")
    public ArrayList<UnitValueBean> ebitda;     //ebitda

    @Expose
    @SerializedName("industry_Classification")
    public ArrayList<CodeNameBean> industry_Classification;   //行业名称

    @Expose
    @SerializedName("location")
    public ArrayList<CodeNameBean> location;   //地理位置

    @Expose
    @SerializedName("market_Share")
    public MarketShareBean market_Share;     //市场占有率，市场份额

    @Expose
    @SerializedName("netProfit")
    public ArrayList<UnitValueBean> netProfit;      //净利润,利润

    @Expose
    @SerializedName("other_key_index")
    public ArrayList<UnitNumIndexBean> otherKeyIndex;        //其他关键指标

    @Expose
    @SerializedName("price")
    public ArrayList<UnitValueBean> price;      //交易价格

    @Expose
    @SerializedName("product_productWords")
    public ArrayList<CodeNameBean> product_productWords;        //产品名称

    @Expose
    @SerializedName("profitMargin")
    public ArrayList<ValueBean> profitMargin;      //利润率

    @Expose
    @SerializedName("publisherInfo")
    public PublisherInfoBean publisherInfo;        //发布者信息

    @Expose
    @SerializedName("turnover")
    public ArrayList<UnitValueModifierBean> turnover;      //营业额

    @Expose
    @SerializedName("info_source")
    public String info_source;      //项目需求来源

    @Expose
    @SerializedName("entity_type")
    public String entity_type;      //买方类型

    @Expose
    @SerializedName("isGain")
    public boolean isGain;//是否盈利

    @Expose
    @SerializedName("num_employee")
    public String num_employee;//企业人数

    @Expose
    @SerializedName("round")
    public ArrayList<String> round;//融资轮次

    @Expose
    @SerializedName("year_established")
    public String year_established;//成立年份

    @Expose
    @SerializedName("cashFlow")
    public ArrayList<UnitValueBean> cashFlow;//现金流


}