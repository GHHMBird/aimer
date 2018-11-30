package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/4
 */
class GetFriendListResponse : Serializable {


    @Expose
    @SerializedName("scanCode")
    var scanCode: String? = null

    @Expose
    @SerializedName("avatars")
    var avatars: String? = null


    @Expose
    @SerializedName("name")
    var name: String? = null


    @Expose
    @SerializedName("companyName")
    var companyName: String? = null


    @Expose
    @SerializedName("position")
    var position: String? = null

    @Expose
    @SerializedName("profileList")
    var profileList: ArrayList<GetFriendListBean>? = null


}