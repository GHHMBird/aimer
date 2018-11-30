package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/28
 */
class PostUserInfoType3Request : RequestData(), Serializable {

    @Expose
    @SerializedName("avatars")
    var avatars: String? = null

    @Expose
    @SerializedName("businessArea")
    var businessArea: String? = null

    @Expose
    @SerializedName("companyId")
    var companyId: String? = null

    @Expose
    @SerializedName("companyName")
    var companyName: String? = null

    @Expose
    @SerializedName("companyWebSite")
    var companyWebSite: String? = null

    @Expose
    @SerializedName("password")
    var password: String? = null

    @Expose
    @SerializedName("position")
    var position: String? = null

    @Expose
    @SerializedName("userName")
    var userName: String? = null

    @Expose
    @SerializedName("workEmail")
    var workEmail: String? = null

}