package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/5/16
 */

public class MatchCompanyMorePageResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public ArrayList<MatchCompanyBean> data;



}
