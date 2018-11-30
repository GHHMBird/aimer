package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/15
 */

public class IndustrySuperiorResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public ArrayList<IndustrySuperiorModel> data;



}
