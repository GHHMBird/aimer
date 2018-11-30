package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/9.
 */

public class MarketShareBean implements Serializable {
    @Expose
    @SerializedName("marketShareQuantity")
    public ArrayList<String> marketShareQuantity;

}
