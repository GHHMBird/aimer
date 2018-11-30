package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/5/16.
 */

public class MatchCompanyMorePageRequest extends RequestData {


    @Expose
    @SerializedName("pageNum")
    public int pageNum;

    @Expose
    @SerializedName("pageSize")
    public int pageSize;

    @Expose
    @SerializedName("projectId")
    public String projectId;


}
