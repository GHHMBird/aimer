package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/24
 */
class InviteLawyerBean : Serializable {


    @Expose
    @SerializedName("email")
    var email: String? = null

    @Expose
    @SerializedName("instituteName")
    var instituteName: String? = null

    @Expose
    @SerializedName("instituteSite")
    var instituteSite: String? = null

    @Expose
    @SerializedName("lawyerId")
    var lawyerId: String? = null

    @Expose
    @SerializedName("lawyerName")
    var lawyerName: String? = null

    @Expose
    @SerializedName("position")
    var position: String? = null


}