package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/15
 */
class RelationNode : Serializable {


    @Expose
    @SerializedName("date")
    var date: String? = null

    @Expose
    @SerializedName("id")
    var name: String? = null

    @Expose
    @SerializedName("name")
    var id: String? = null


}