package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.ResponseData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationGetMessageResponse : ResponseData() {


    @Expose
    @SerializedName("data")
    var data: AccountVerificationGetMessageModel? = null


}