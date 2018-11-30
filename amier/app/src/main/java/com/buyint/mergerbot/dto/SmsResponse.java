package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/4/8
 */

public class SmsResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public SmsBean data;


}
