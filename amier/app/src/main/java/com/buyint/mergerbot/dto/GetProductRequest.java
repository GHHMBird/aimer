package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/19.
 */

public class GetProductRequest extends RequestData {


    @Expose
    @SerializedName("industry_Classification")
    public ArrayList<CodeNameBean> industry_Classification;


    @Expose
    @SerializedName("userIntentions")
    public ArrayList<IntentionBean> userIntentions;

}
