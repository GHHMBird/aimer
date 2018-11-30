package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huheming on 2018/6/15
 */

public class MatchRateBean {


    @Expose
    @SerializedName("industryRate")
    public double industryRate;

    @Expose
    @SerializedName("matchRate")
    public double matchRate;

    @Expose
    @SerializedName("priceRate")
    public double priceRate;

    @Expose
    @SerializedName("productRate")
    public double productRate;


}
