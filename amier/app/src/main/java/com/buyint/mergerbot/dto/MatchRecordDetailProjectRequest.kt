package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordDetailProjectRequest : RequestData(), Serializable {


    @Expose
    @SerializedName("pageNum")
    var pageNum: Int? = null

    @Expose
    @SerializedName("pageSize")
    var pageSize: Int? = null

    @Expose
    @SerializedName("userId")
    var userId: String? = null


}