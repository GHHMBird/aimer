package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/18
 */
class ChangeQATrainAimerRequest : Serializable {


    @Expose
    @SerializedName("newSentence")
    var newSentence: String? = null


    @Expose
    @SerializedName("qid")
    var qid: String? = null

    @Expose
    @SerializedName("sendTime")
    var sendTime: Long? = null


    @Expose
    @SerializedName("sentence")
    var sentence: String? = null


}