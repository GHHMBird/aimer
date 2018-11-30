package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/9
 */
class MatchRecordDetailClientModel : Serializable {


    @Expose
    @SerializedName("address")
    var address: String? = null


    @Expose
    @SerializedName("id")
    var id: String? = null

    @Expose
    @SerializedName("industry")
    var industry: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("product")
    var product: String? = null

    @Expose
    @SerializedName("scale")
    var scale: String? = null

    @Expose
    @SerializedName("members")
    var members: ArrayList<MatchRecordListModel>? = null


}