package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/6/8.
 */

public class NameTitleBean implements Serializable {

    @Expose
    @SerializedName("title")
    public String title;

    @Expose
    @SerializedName("name")
    public String name;

}
