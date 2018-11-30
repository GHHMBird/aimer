package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationGetMessageModel : Serializable {


    @Expose
    @SerializedName("id")
    var id: String? = null


    @Expose
    @SerializedName("authenticationResult")
    var authenticationResult : String? = null

}