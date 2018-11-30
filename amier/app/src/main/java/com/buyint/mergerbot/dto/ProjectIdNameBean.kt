package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/28
 */
class ProjectIdNameBean : Serializable {


    @Expose
    @SerializedName("projectId")
    var projectId: String? = null

    @Expose
    @SerializedName("projectName")
    var projectName: String? = null


}