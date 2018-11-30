package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.ResponseData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by huheming on 2018/8/14
 */
class GetNameDetailResponse : ResponseData() {


    @Expose
    @SerializedName("data")
    var data: ArrayList<GetNameDetailModel>? = null


}