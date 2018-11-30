package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/9.
 */

public class UnitValueModifierBean implements Serializable {


    @Expose
    @SerializedName("modifiers")
    public String modifiers = "ABOUT";

    @Expose
    @SerializedName("unit")
    public String unit;

    @Expose
    @SerializedName("value")
    public double value;

}
