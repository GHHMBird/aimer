package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/17
 */
class UserProjectsRate :Serializable{

    @Expose
    @SerializedName("industryRate")
    var industryRate: Double? = null


    @Expose
    @SerializedName("matchRate")
    var matchRate: Double? = null


    @Expose
    @SerializedName("matchedDate")
    var matchedDate: Double? = null


    @Expose
    @SerializedName("priceRate")
    var priceRate: Double? = null


    @Expose
    @SerializedName("productRate")
    var productRate: Double? = null


    @Expose
    @SerializedName("projectId")
    var projectId: String? = null


}