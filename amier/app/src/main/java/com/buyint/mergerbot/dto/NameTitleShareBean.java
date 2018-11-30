package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/6/14
 */

public class NameTitleShareBean extends NameTitleBean implements Serializable {


    @Expose
    @SerializedName("share")
    public double share;


}
