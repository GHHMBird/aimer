package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/9.
 */

public class ValueBean implements Serializable {

    @Expose
    @SerializedName("value")
    public double value;
}
