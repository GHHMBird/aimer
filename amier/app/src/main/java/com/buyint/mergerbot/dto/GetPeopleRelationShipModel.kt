package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/8/15
 */
class  GetPeopleRelationShipModel : Serializable {


    @Expose
    @SerializedName("links")
    var links: ArrayList<RelationLine>? = null

    @Expose
    @SerializedName("nodes")
    var nodes: ArrayList<RelationNode>? = null

}