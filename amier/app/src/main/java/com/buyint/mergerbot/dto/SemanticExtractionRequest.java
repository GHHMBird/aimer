package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CXC on 2018/4/20.
 */

public class SemanticExtractionRequest extends RequestData {


    @Expose
    @SerializedName("text")
    public String text;

}
