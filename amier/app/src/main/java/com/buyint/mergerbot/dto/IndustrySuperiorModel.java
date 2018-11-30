package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/15
 */

public class IndustrySuperiorModel implements Serializable {


    @Expose
    @SerializedName("avatarUrl")
    public String avatarUrl;


    @Expose
    @SerializedName("discription")
    public String discription;


    @Expose
    @SerializedName("companyName")
    public String companyName;


    @Expose
    @SerializedName("role")
    public String role;


    @Expose
    @SerializedName("name")
    public String name;


    @Expose
    @SerializedName("p_id")
    public String p_id;


}
