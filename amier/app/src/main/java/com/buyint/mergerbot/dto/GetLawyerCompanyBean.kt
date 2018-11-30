package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/23
 */
class GetLawyerCompanyBean : Serializable {


    @Expose
    @SerializedName("law_id")
    var law_id: String? = null

    @Expose
    @SerializedName("name")
    var name = ""


}