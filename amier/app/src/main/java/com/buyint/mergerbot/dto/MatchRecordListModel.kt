package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.ResponseData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordListModel : ResponseData(), Serializable {

    @Expose
    @SerializedName("businessFriend")
    var businessFriend: Int? = 0

    @Expose
    @SerializedName("company")
    var company: Int? = 0

    @Expose
    @SerializedName("project")
    var project: Int? = 0

    @Expose
    @SerializedName("companyName")
    var companyName: String? = null

    @Expose
    @SerializedName("domain")
    var domain: String? = null

    @Expose
    @SerializedName("industry")
    var industry: String? = null

    @Expose
    @SerializedName("personId")
    var personId: String? = null

    @Expose
    @SerializedName("position")
    var position: String? = null

    @Expose
    @SerializedName("userId")
    var userId: String? = null

    @Expose
    @SerializedName("userName")
    var userName: String? = null

    @Expose
    @SerializedName("reliableRate")
    var reliableRate: Double? = 0.0

    @Expose
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @Expose
    @SerializedName("latestnew")
    var latestnew: String? = null

    @Expose
    @SerializedName("suggest")
    var suggest: Int? = 0

    @Expose
    @SerializedName("relationType")
    var relationType: String? = null


}