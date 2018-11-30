package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/8
 */

public class LoginModel implements Serializable {

    @Expose
    @SerializedName("matchRecordPersonId")
    public String matchRecordPersonId;

    @Expose
    @SerializedName("userName")
    public String userName;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("description")
    public String description;

    @Expose
    @SerializedName("englishName")
    public String englishName;

    @Expose
    @SerializedName("uuid")
    public String uuid;

    @Expose
    @SerializedName("phone")
    public String phone;

    @Expose
    @SerializedName("enable")
    public String enable;

    @Expose
    @SerializedName("userMessageLevel")
    public String userMessageLevel;

    @Expose
    @SerializedName("nickName")
    public String nickName;

    @Expose
    @SerializedName("avatars")
    public String avatars;

    @Expose
    @SerializedName("email")
    public ArrayList<String> email;

    @Expose
    @SerializedName("token")
    public String token;

    @Expose
    @SerializedName("linkedInId")
    public String linkedInId;

    @Expose
    @SerializedName("registerIndustryClassification")
    public ArrayList<CodeNameBean> registerIndustryClassification;

    @Expose
    @SerializedName("userIntentions")
    public ArrayList<IntentionBean> userIntentions;

    @Expose
    @SerializedName("userWorkMessage")
    public UserNikeAndIntentionDto userWorkMessage;

    @Expose
    @SerializedName("userPrivacySetting")
    public UpdatePrivacySettingModel userPrivacySetting;

    @Expose
    @SerializedName("linkedInInfo")
    public LinkedInInfo linkedInInfo;

    @Expose
    @SerializedName("authentication")
    public boolean authentication;

    public String loginCount;


}
