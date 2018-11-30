package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by huheming on 2018/8/8
 */
class MatchRecordLockRequest : RequestData() {


    @Expose
    @SerializedName("companyName")
    var companyName: String? = null

    @Expose
    @SerializedName("userName")
    var userName: String? = null

    @Expose
    @SerializedName("personId")
    var personId: String? = null


}