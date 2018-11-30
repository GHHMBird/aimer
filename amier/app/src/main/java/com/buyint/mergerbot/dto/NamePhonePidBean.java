package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/10/11
 */

public class NamePhonePidBean implements Serializable {


    @Expose
    @SerializedName("pid")
    public String pid;

    @Expose
    @SerializedName("phone")
    public String phone;

    @Expose
    @SerializedName("name")
    public String name;

}
