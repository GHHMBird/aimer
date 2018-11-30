package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/7/4
 */

public class LinkedInInfo implements Serializable {


    @Expose
    @SerializedName("emailAddress")
    public String emailAddress;


    @Expose
    @SerializedName("formattedName")
    public String formattedName;


    @Expose
    @SerializedName("industry")
    public String industry;


    @Expose
    @SerializedName("linkedInId")
    public String linkedInId;


    @Expose
    @SerializedName("location")
    public String location;


    @Expose
    @SerializedName("phoneNumber")
    public String phoneNumber;

}
