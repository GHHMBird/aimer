package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ABC implements Serializable {

    @Expose
    @SerializedName("companyName")
    public String companyName;

    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("position")
    public String position;

    @Expose
    @SerializedName("reliableRate")
    public double reliableRate;

    @Expose
    @SerializedName("userName")
    public String userName;


}