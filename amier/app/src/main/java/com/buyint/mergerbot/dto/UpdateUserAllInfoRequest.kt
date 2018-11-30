package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/12
 */
class UpdateUserAllInfoRequest : RequestData(), Serializable {

    @Expose
    @SerializedName("description")
    var description: String? = null

    @Expose
    @SerializedName("englishName")
    var englishName: String? = null

    @Expose
    @SerializedName("registerIndustryClassification")
    var registerIndustryClassification: ArrayList<CodeNameBean>? = null

    @Expose
    @SerializedName("showNameType")
    var showNameType: String? = null

    @Expose
    @SerializedName("userName")
    var userName: String? = null

    @Expose
    @SerializedName("userWorkMessage")
    var userWorkMessage: UserNikeAndIntentionDto? = null

}