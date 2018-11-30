package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/12
 */

public class MatchCompanyBean implements Serializable {


    @Expose
    @SerializedName("similarityCompany")
    public String similarityCompany;

    @Expose
    @SerializedName("matchRate")
    public String matchRate;


    @Expose
    @SerializedName("matchedDate")
    public long matchedDate;


    @Expose
    @SerializedName("reliableRate")
    public String reliableRate;


    @Expose
    @SerializedName("matchedCompanyNames")
    public ArrayList<String> matchedCompanyNames;

    @Expose
    @SerializedName("projectInfo")
    public ProjectInfoBean projectInfo;

    @Expose
    @SerializedName("userInfo")
    public UserInfoBean userInfo;

    @Expose
    @SerializedName("attention")
    public boolean attention;

    @Expose
    @SerializedName("projectSource")
    public String projectSource;

    public String type = "userRecommend";

    @Expose
    @SerializedName("matchedProjectType")
    public String matchedProjectType;

    @Expose
    @SerializedName("matchRecordCompanyItem")
    public MatchRecordDetailClientModel matchRecordCompanyItem;

    @Expose
    @SerializedName("matchedUserListItem")
    public ABC matchedUserListItem;

}
