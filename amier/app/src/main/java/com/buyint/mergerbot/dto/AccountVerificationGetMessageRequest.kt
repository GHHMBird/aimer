package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationGetMessageRequest : RequestData() {


    @Expose
    @SerializedName("authenticationCode")
    var authenticationCode: String? = null


    @Expose
    @SerializedName("companyEmail")
    var companyEmail: String? = null


    @Expose
    @SerializedName("companyName")
    var companyName: String? = null


    @Expose
    @SerializedName("companySite")
    var companySite: String? = null


    @Expose
    @SerializedName("position")
    var position: String? = null

    @Expose
    @SerializedName("realName")
    var realName: String? = null

    @Expose
    @SerializedName("authenticationType")
    var authenticationType: ArrayList<String>? = null


}