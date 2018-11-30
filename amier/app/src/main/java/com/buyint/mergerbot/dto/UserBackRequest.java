package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huheming on 2018/6/6.
 */

public class UserBackRequest extends RequestData {


    @Expose
    @SerializedName("content")
    public String content ;

    @Expose
    @SerializedName("mail")
    public String mail;

}
