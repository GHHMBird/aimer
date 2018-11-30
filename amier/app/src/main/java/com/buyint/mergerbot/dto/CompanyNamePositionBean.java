package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/6/14
 */

public class CompanyNamePositionBean implements Serializable {


    @Expose
    @SerializedName("companyName")
    public String companyName;


    @Expose
    @SerializedName("position")
    public String position;

}
