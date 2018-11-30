package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/24
 */
class SendMessageToCreateLawyerRequest : Serializable {

    @Expose
    @SerializedName("emailContent")
    var emailContent: String? = null


    @Expose
    @SerializedName("lawyerAuthenticationMessage")
    var lawyerAuthenticationMessage: AmiAccessBean? = null


    @Expose
    @SerializedName("invitelayers")
    var invitelayers: InviteLawyerBean? = null


}