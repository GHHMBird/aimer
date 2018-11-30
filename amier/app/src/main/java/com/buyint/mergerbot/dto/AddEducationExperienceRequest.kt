package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/23
 */
class AddEducationExperienceRequest : RequestData(), Serializable {


    @Expose
    @SerializedName("description")
    var description: String? = null


    @Expose
    @SerializedName("id")
    var id: String? = null


    @Expose
    @SerializedName("educationBackground")
    var educationBackground: String? = null


    @Expose
    @SerializedName("endTime")
    var endTime: Long? = null


    @Expose
    @SerializedName("professional")
    var professional: ArrayList<String>? = null


    @Expose
    @SerializedName("schoolName")
    var schoolName: String? = null


    @Expose
    @SerializedName("startTime")
    var startTime: Long? = null


    @Expose
    @SerializedName("unifiedEntrance")
    var unifiedEntrance: Boolean? = null


}