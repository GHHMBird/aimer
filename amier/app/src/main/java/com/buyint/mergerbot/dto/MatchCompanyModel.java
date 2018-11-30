package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/12
 */

public class MatchCompanyModel implements Serializable {


    @Expose
    @SerializedName("projectId")
    public String projectId;


    @Expose
    @SerializedName("relationProject")
    public ArrayList<MatchCompanyBean> relationProject;


}
