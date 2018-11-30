package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/20
 */
class PutUserBusinessActivityRequest : RequestData(), Serializable {


    @Expose
    @SerializedName("activityAddress")
    var activityAddress: String? = null


    @Expose
    @SerializedName("activityDescription")
    var activityDescription: String? = null


    @Expose
    @SerializedName("activityName")
    var activityName: String? = null


    @Expose
    @SerializedName("activityType")
    var activityType: ArrayList<String>? = null


    @Expose
    @SerializedName("startTime")
    var startTime: Long? = null


    @Expose
    @SerializedName("parties")
    var parties: ArrayList<String>? = null


    @Expose
    @SerializedName("endTime")
    var endTime: Long? = null


    @Expose
    @SerializedName("id")
    var id: String? = null


}