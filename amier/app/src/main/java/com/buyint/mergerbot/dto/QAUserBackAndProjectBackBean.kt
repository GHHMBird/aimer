package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/7
 */
class QAUserBackAndProjectBackBean:Serializable {


    @Expose
    @SerializedName("text")
    var text: String? = null

    @Expose
    @SerializedName("qid")
    var qid: String? = null

    @Expose
    @SerializedName("type")
    var type: String? = null

}