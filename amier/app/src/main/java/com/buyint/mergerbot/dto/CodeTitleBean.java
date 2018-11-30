package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/10
 */

public class CodeTitleBean implements Serializable {


    @Expose
    @SerializedName("code")
    public String code;


    @Expose
    @SerializedName("name")
    public ArrayList<String> name;
}
