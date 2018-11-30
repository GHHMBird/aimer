package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/20.
 */

public class UserInfoBean implements Serializable {
    @Expose
    @SerializedName("avatars")
    public String avatars;

  @Expose
    @SerializedName("companyName")
    public String companyName;

  @Expose
    @SerializedName("duty")
    public String duty;

  @Expose
    @SerializedName("nickName")
    public String nickName;

}
