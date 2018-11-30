package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/11.
 */

public class MatchCompanyDetailBean implements Serializable {


    @Expose
    @SerializedName("createdTime")
    public long createdTime;//创建时间

    @Expose
    @SerializedName("description")
    public String description;//描述

    @Expose
    @SerializedName("industryClassification")
    public String industryClassification;//行业

    @Expose
    @SerializedName("location")
    public String location;

    @Expose
    @SerializedName("matchRate")
    public String matchRate;//匹配度

    @Expose
    @SerializedName("pojectName")
    public String pojectName;//项目名

    @Expose
    @SerializedName("price")
    public String price;

    @Expose
    @SerializedName("reliableRate")
    public String reliableRate;//靠谱度

    @Expose
    @SerializedName("similarCompanyCount")
    public int similarCompanyCount;//相似公司数量

    @Expose
    @SerializedName("userCompanyName")
    public String userCompanyName;//公司名

    @Expose
    @SerializedName("userName")
    public String userName;//用户姓名

    @Expose
    @SerializedName("userPosition")
    public String userPosition;//用户职位

    @Expose
    @SerializedName("projectSource")
    public String projectSource;//来源

    @Expose
    @SerializedName("webSite")
    public String webSite;//官网

    @Expose
    @SerializedName("sourceWeb")
    public String sourceWeb ;

    @Expose
    @SerializedName("intention")
    public String intention;//意图


    @Expose
    @SerializedName("matchRateObj")
    public MatchRateBean matchRateObj ;

    @Expose
    @SerializedName("attention")
    public boolean attention;//关注

    @Expose
    @SerializedName("userAvatars")
    public String userAvatars;


    @Expose
    @SerializedName("companyInfoDtoList")
    public ArrayList<SimilarCompanyBean> companyInfoDtoList;//相似公司

    @Expose
    @SerializedName("institutionDtoList")
    public ArrayList<SimilarMechanismBean> institutionDtoList;//相似机构

    @Expose
    @SerializedName("industrySuperiorList")
    public ArrayList<IndustrySuperiorModel> industrySuperiorList;


}
