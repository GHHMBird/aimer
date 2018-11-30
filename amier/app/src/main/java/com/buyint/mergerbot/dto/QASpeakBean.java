package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/17
 */

public class QASpeakBean extends MatchCompanyModel {

    @Expose
    @SerializedName("sentence")
    public ArrayList<String> sentence;

    @Expose
    @SerializedName("status")
    public int status;

    @Expose
    @SerializedName("askPrice")
    public boolean askPrice;

    @Expose
    @SerializedName("searchKey")
    public String searchKey;

    @Expose
    @SerializedName("intention")
    public ArrayList<String> intention;

    @Expose
    @SerializedName("requirementOrCondition")
    public MatchCompanyRequestBean requirementOrCondition;

}
