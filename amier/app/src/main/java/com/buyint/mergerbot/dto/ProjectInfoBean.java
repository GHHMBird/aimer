package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/20
 */

public class ProjectInfoBean implements Serializable {

    @Expose
    @SerializedName("uuid")
    public String uuid;

    @Expose
    @SerializedName("qid")
    public String qid;

    @Expose
    @SerializedName("description")
    public String description;//描述

    @Expose
    @SerializedName("projectName")
    public String projectName;//项目名称

    @Expose
    @SerializedName("intention")
    public ArrayList<String> intention;//购买或者出售意图

    @Expose
    @SerializedName("condition")
    public MatchCompanyRequestBean condition;//当前填写人的信息

    @Expose
    @SerializedName("requirement")
    public MatchCompanyRequestBean requirement;//要求对方的信息


}
