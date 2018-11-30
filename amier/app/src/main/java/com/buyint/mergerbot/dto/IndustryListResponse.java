package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/10
 */

public class IndustryListResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public ArrayList<CodeTitleBean> data;


}
