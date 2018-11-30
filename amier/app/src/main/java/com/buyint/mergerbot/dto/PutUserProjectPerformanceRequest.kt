package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by huheming on 2018/7/20
 */
class PutUserProjectPerformanceRequest : RequestData(), Serializable {


    @Expose
    @SerializedName("companyAddress")
    var companyAddress: String? = null


    @Expose
    @SerializedName("companyName")
    var companyName: String? = null


    @Expose
    @SerializedName("endTime")
    var endTime: Long? = null


    @Expose
    @SerializedName("parties")
    var parties: ArrayList<String>? = null


    @Expose
    @SerializedName("positionName")
    var positionName: String? = null


    @Expose
    @SerializedName("projectAchievement")
    var projectAchievement: String? = null


    @Expose
    @SerializedName("projectDescription")
    var projectDescription: String? = null


    @Expose
    @SerializedName("projectName")
    var projectName: String? = null


    @Expose
    @SerializedName("projectProfession")
    var projectProfession: String? = null


    @Expose
    @SerializedName("relatedPersonnel")
    var relatedPersonnel: ArrayList<String>? = null


    @Expose
    @SerializedName("startTime")
    var startTime: Long? = null


    @Expose
    @SerializedName("id")
    var id: String? = null


    @Expose
    @SerializedName("transactionType")
    var transactionType: ArrayList<String>? = null

}