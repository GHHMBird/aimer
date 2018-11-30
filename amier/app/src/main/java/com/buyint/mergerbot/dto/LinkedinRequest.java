package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/13.
 */

public class LinkedinRequest extends RequestData {

    @Expose
    @SerializedName("emailAddress")
    public String emailAddress;//邮箱
    @Expose
    @SerializedName("formattedName")
    public String formattedName;//名字
    @Expose
    @SerializedName("industry")
    public String industry;//邮箱
    @Expose
    @SerializedName("linkedInId")
    public String linkedInId;//
    @Expose
    @SerializedName("location")
    public String location;//邮箱
    @Expose
    @SerializedName("phoneNumber")
    public String phoneNumber;//邮箱
    @Expose
    @SerializedName("position")
    public ArrayList<CompanyNamePositionBean> position;//
    // @Expose
    @SerializedName("accessToken")
    public String accessToken;//
}
