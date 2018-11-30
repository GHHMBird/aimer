package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CXC on 2018/4/23.
 */

public class VersionBean implements Serializable {


    @Expose
    @SerializedName("version")
    public String version;

    @Expose
    @SerializedName("apkPath")
    public String apkPath;


}
