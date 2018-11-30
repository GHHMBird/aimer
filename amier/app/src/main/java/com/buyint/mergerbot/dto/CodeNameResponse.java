package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/11.
 */

public class CodeNameResponse extends ResponseData implements Serializable{

    @Expose
    @SerializedName("data")
    public ArrayList<CodeNameBean> data;

}
