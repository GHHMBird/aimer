package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huheming on 2018/7/4
 */

public class AccountMergerRequest extends RequestData {


    @Expose
    @SerializedName("mergeType")
    public String mergeType;


    @Expose
    @SerializedName("mergedAccount")
    public String mergedAccount;


    @Expose
    @SerializedName("newPassword")
    public String newPassword;


    @Expose
    @SerializedName("verificationCode")
    public String verificationCode;


    @Expose
    @SerializedName("accessToken")
    public String accessToken;

}
