package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/5/15.
 */

public class RegisterVerificationResponse extends ResponseData {

    @Expose
    @SerializedName("data")
    public String data;


}
