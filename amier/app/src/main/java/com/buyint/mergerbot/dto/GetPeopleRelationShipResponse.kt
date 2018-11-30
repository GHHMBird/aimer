package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/15
 */
class GetPeopleRelationShipResponse :Serializable {


    @Expose
    @SerializedName("data")
    var data: GetPeopleRelationShipModel? = null


}