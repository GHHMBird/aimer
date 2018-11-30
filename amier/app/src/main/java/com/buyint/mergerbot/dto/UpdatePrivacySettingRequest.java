package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huheming on 2018/7/12
 */

public class UpdatePrivacySettingRequest extends RequestData implements Serializable{

    @Expose
    @SerializedName("userPrivacySetting")
    public UpdatePrivacySettingModel userPrivacySetting ;

}
