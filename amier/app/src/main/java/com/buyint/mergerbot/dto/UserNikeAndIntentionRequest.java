package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/15
 */

public class UserNikeAndIntentionRequest extends RequestData {

    @Expose
    @SerializedName("registerIndustryClassification")
    public ArrayList<CodeNameBean> registerIndustryClassification;

    @Expose
    @SerializedName("userIntentions")
    public ArrayList<IntentionBean> userIntentions;

    @Expose
    @SerializedName("nickName")
    public String nickName;

    @Expose
    @SerializedName("avatars")
    public String avatars;

    @Expose
    @SerializedName("userWorkMessage")
    public UserNikeAndIntentionDto userWorkMessage;

}
