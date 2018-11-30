package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/10/17
 */

public class MatchRecordDeepMatchRequest implements Serializable {


    @Expose
    @SerializedName("companyName")
  public  String companyName = null;

    @Expose
    @SerializedName("enjoyProject")
    public  String enjoyProject = null;

    @Expose
    @SerializedName("name")
    public String name = null;

    @Expose
    @SerializedName("onceInCompany")
    public String onceInCompany = null;

    @Expose
    @SerializedName("position")
    public  String position = null;


}
