package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/7/2
 */

public class VerifyCodeChangePasswordRequest extends RequestData implements Serializable {

    @Expose
    @SerializedName("account")
    public String account;

    @Expose
    @SerializedName("password")
    public String password;

    @Expose
    @SerializedName("verificationCode")
    public String verificationCode;


}
