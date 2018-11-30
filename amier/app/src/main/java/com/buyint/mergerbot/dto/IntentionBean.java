package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/19
 */

public class IntentionBean implements Serializable {

    @Expose
    @SerializedName("intentionCode")
    public String intentionCode;

    @Expose
    @SerializedName("intentionName")
    public String intentionName;

    @Expose
    @SerializedName("childIntention")
    public ArrayList<String> childIntention;

}
