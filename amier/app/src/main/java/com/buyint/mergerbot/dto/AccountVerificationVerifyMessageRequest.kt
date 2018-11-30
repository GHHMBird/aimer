package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationVerifyMessageRequest: RequestData() {


    @Expose
    @SerializedName("code")
    var code: String? = null


    @Expose
    @SerializedName("id")
    var id: String? = null



}