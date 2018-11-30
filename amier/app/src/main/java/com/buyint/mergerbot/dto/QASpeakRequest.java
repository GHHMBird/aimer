package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/5/17
 */

public class QASpeakRequest extends RequestData {


    @Expose
    @SerializedName("projectId")
    public String projectId;

    @Expose
    @SerializedName("text")
    public String text;

    @Expose
    @SerializedName("project_price")
    public String project_price;

    @Expose
    @SerializedName("project_profit")
    public String project_profit;

}
