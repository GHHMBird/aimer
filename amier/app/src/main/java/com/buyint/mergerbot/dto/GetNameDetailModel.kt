package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/14
 */
class GetNameDetailModel : Serializable {


    @Expose
    @SerializedName("company")
    var company: String? = null


    @Expose
    @SerializedName("name")
    var name: String? = null


    @Expose
    @SerializedName("p_id")
    var p_id: String? = null


    @Expose
    @SerializedName("role")
    var role: String? = null


    @Expose
    @SerializedName("title")
    var title: ArrayList<String>? = null


}