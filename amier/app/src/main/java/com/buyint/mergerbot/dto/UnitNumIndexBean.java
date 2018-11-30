package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/9.
 */

public class UnitNumIndexBean implements Serializable {


    @Expose
    @SerializedName("index_name")
    public String index_name;

    @Expose
    @SerializedName("number")
    public double number;

    @Expose
    @SerializedName("unit")
    public String unit;
}
