package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/17
 */
class FromToTimeBean : Serializable {

    @Expose
    @SerializedName("from")
    var from: ArrayList<String>? = null

    @Expose
    @SerializedName("to")
    var to: ArrayList<String>? = null

    @Expose
    @SerializedName("updateTime")
    var updateTime: Long? = null

}