package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/6/22
 */

public class UserNikeAndIntentionDto implements Serializable {

    @Expose
    @SerializedName("businessDivision")
    public String businessDivision;

    @Expose
    @SerializedName("companyName")
    public String companyName;

    @Expose
    @SerializedName("companyWebSite")
    public String companyWebSite;

    @Expose
    @SerializedName("mainBusiness")
    public String mainBusiness;

    @Expose
    @SerializedName("position")
    public String position;

    @Expose
    @SerializedName("workAddress")
    public String workAddress;

    @Expose
    @SerializedName("workEmail")
    public String workEmail;

}
