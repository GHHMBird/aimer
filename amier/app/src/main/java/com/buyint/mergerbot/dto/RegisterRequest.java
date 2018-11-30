package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/3/23
 */

public class RegisterRequest extends RequestData {


    @Expose
    @SerializedName("phoneNumber")
    public String phoneNumber;

    @Expose
    @SerializedName("verificationCode")
    public String verificationCode;

}
