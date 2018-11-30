package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/17
 */
class DialogueSentenceBean : Serializable {

//    @Expose
//    @SerializedName("_class")
//    var _class: String? = null

    @Expose
    @SerializedName("dialogueHistory")
    var dialogueHistory: ArrayList<FromToTimeBean>? = null

    @Expose
    @SerializedName("from")
    var from: String? = null

    @Expose
    @SerializedName("sentence")
    var sentence: ArrayList<String>? = null

    @Expose
    @SerializedName("sendTime")
    var sendTime: Long? = null

}