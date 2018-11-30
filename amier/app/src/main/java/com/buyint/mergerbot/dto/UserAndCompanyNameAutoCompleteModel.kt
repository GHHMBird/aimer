package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/26
 */
class UserAndCompanyNameAutoCompleteModel : Serializable {


    @Expose
    @SerializedName("companyNames")
    var companyNames: ArrayList<String>? = null


    @Expose
    @SerializedName("userNames")
    var userNames: ArrayList<CodeNameBean>? = null


}