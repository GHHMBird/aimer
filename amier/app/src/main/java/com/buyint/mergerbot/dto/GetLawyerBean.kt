package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/23
 */
class GetLawyerBean:Serializable {

    @Expose
    @SerializedName("company")
    var company = ""

    @Expose
    @SerializedName("email")
    var email = ""

    @Expose
    @SerializedName("id")
    var id = ""

    @Expose
    @SerializedName("name")
    var name = ""

    @Expose
    @SerializedName("position")
    var position = ""

    @Expose
    @SerializedName("source")
    var source = ""


}