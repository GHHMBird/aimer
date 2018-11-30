package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/4/9
 */

public class MatchCompanyResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public MatchCompanyModel data;

    @Expose
    @SerializedName("pageSize")
    public int pageSize;

    @Expose
    @SerializedName("pageNum")
    public int pageNum;

    @Expose
    @SerializedName("pages")
    public int pages;

    @Expose
    @SerializedName("total")
    public int total;

}
