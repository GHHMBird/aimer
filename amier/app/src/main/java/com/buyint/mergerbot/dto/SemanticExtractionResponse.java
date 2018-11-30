package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.ResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/4/20
 */

public class SemanticExtractionResponse extends ResponseData {


    @Expose
    @SerializedName("data")
    public SemanticExtractionBean data;


}
