package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/8.
 */

public class SimilarMechanismBean extends SimilarBean implements Serializable {


    @Expose
    @SerializedName("cid")
    public String cid;//

    @Expose
    @SerializedName("descrip")
    public String descrip;

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("foundTime")
    public long foundTime;

    @Expose
    @SerializedName("geoLabel")
    public String geoLabel;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("website")
    public String website;

    @Expose
    @SerializedName("logoUrl")
    public String logoUrl;

    @Expose
    @SerializedName("coreTeam")
    public ArrayList<NameTitleShareBean> coreTeam;


}
