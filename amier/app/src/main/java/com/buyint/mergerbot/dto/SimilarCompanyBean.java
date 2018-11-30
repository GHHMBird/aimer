package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/8.
 */

public class SimilarCompanyBean extends SimilarBean implements Serializable {


    @Expose
    @SerializedName("cid")
    public String cid;//

    @Expose
    @SerializedName("companyTicket")
    public String companyTicket;

    @Expose
    @SerializedName("descrip")
    public String descrip;

    @Expose
    @SerializedName("location")
    public String location;

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
    @SerializedName("shareholders")
    public ArrayList<NameTitleShareBean> shareholder;

}
