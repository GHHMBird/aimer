package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/15
 */
class RelationLine : Serializable {


    @Expose
    @SerializedName("relation")
    var relation: String? = null


    @Expose
    @SerializedName("source")
    var source: String? = null


    @Expose
    @SerializedName("target")
    var target: String? = null


    @Expose
    @SerializedName("value")
    var value: String? = null


}