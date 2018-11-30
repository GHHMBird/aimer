package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/19.
 */

public class SmsBean implements Serializable {

    @Expose
    @SerializedName("phoneNumberExit")
    public boolean phoneNumberExit;


}
