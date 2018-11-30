package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.ResponseData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/6/26
 */
class StringResponse : ResponseData(), Serializable {

    @Expose
    @SerializedName("data")
    var data: String? = null


}