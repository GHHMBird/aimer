package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/9
 */

public class UnitValueBean implements Serializable {

    @Expose
    @SerializedName("unit")
    public String unit;

    @Expose
    @SerializedName("value")
    public double value;

}
