package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/9
 */

public class MatchCompanyRequest extends RequestData {

    @Expose
    @SerializedName("projectCategory")
    public int projectCategory;//项目类型  1深度匹配 0快速匹配

    @Expose
    @SerializedName("description")
    public String description;//描述

    @Expose
    @SerializedName("projectName")
    public String projectName;//项目名称

    @Expose
    @SerializedName("qid")
    public String qid;//
    @Expose
    @SerializedName("intention")
    public ArrayList<String> intention;//购买或者出售意图

    @Expose
    @SerializedName("condition")
    public MatchCompanyRequestBean condition;//当前填写人的信息

    @Expose
    @SerializedName("requirement")
    public MatchCompanyRequestBean requirement;//要求对方的信息

    @Expose
    @SerializedName("matchType")
    public ArrayList<String> matchType;//匹配类型


}
