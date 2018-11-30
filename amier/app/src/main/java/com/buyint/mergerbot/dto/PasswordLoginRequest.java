package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/5/15
 */

public class PasswordLoginRequest /*extends RequestData*/{


    @Expose
    @SerializedName("password")
    public String password;

    @Expose
    @SerializedName("account")
    public String account;

}
