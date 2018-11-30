package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/7/12
 */

public class UpdatePrivacySettingModel implements Serializable {

    @Expose
    @SerializedName("commercialRecord")
    public ArrayList<String> commercialRecord;

    @Expose
    @SerializedName("contactTime")
    public String contactTime;

    @Expose
    @SerializedName("contactWay")
    public String contactWay;


    @Expose
    @SerializedName("changeMyFile")
    public String changeMyFile;//修改我的档案

    @Expose
    @SerializedName("showNameType")
    public String showNameType;

    @Expose
    @SerializedName("viewCommercialPermission")
    public String viewCommercialPermission;

    @Expose
    @SerializedName("shielding")
    public ArrayList<String> shielding;

}
