package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/4
 */
class GetFriendListBean:Serializable {


    @Expose
    @SerializedName("projectId")
    var projectId: String? = null

    @Expose
    @SerializedName("attention")
    var attention = false


    @Expose
    @SerializedName("projectName")
    var projectName: String? = null

    @Expose
    @SerializedName("matchedRate")
    var matchedRate: ArrayList<GetFriendListBeanMatchRate>? = null


}