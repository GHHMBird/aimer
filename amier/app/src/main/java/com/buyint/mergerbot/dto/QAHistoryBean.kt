package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/7
 */
class QAHistoryBean : Serializable {

    @Expose
    @SerializedName("qid")
    var qid: String? = null

    @Expose
    @SerializedName("projectName")
    var projectName: String? = null

    @Expose
    @SerializedName("createTime")
    var createTime: Long? = 0

    @Expose
    @SerializedName("result")
    var result: Int? = 0

    @Expose
    @SerializedName("projectCategory")
    var projectCategory: Int? = 0//来源 1深度匹配 0快速匹配


}