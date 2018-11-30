package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/4
 */
class GetFriendListBeanMatchRate : Serializable {


    @Expose
    @SerializedName("industry")
    var industry: String? = null


    @Expose
    @SerializedName("price")
    var price: String? = null


    @Expose
    @SerializedName("product")
    var product: String? = null


    @Expose
    @SerializedName("project_id")
    var project_id: String? = null


    @Expose
    @SerializedName("total")
    var total: String? = null


}