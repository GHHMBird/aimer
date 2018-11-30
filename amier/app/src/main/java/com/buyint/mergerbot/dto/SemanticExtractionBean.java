package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/20
 */

public class SemanticExtractionBean implements Serializable {


    @Expose
    @SerializedName("location")
    public ArrayList<CodeNameBean> location;

    @Expose
    @SerializedName("product")
    public ArrayList<CodeNameBean> product;

    @Expose
    @SerializedName("industry")
    public ArrayList<CodeNameBean> industry;

    @Expose
    @SerializedName("scale")
    public String scale;

    @Expose
    @SerializedName("intention")
    public String intention;



}
