package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huheming on 2018/5/30
 */

public class PageRequest extends RequestData {


    @Expose
    @SerializedName("pageNum")
    public int pageNum;

    @Expose
    @SerializedName("pageSize")
    public int pageSize;


    @Expose
    @SerializedName("projectFilterType")
    public String projectFilterType;


}
