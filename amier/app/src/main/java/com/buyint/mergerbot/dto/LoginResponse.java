package com.buyint.mergerbot.dto;


import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/3/23
 */

public class LoginResponse extends ResponseData implements Serializable{

    @Expose
    @SerializedName("data")
    public LoginModel model;
}
