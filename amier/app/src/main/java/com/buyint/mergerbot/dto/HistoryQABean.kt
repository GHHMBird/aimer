package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/17
 */
class HistoryQABean : Serializable {

    @Expose
    @SerializedName("createTime")
    var createTime: Long? = null

    @Expose
    @SerializedName("description")
    var description: String? = null

    @Expose
    @SerializedName("dialogueSentences")
    var dialogueSentences: ArrayList<DialogueSentenceBean>? = null

//    @Expose
//    @SerializedName("features")
//    var features: ? = null

    @Expose
    @SerializedName("lastModifyTime")
    var lastModifyTime: Long? = null

    @Expose
    @SerializedName("net_projects")
    var net_projects: ArrayList<UserProjectsRate>? = null

    @Expose
    @SerializedName("projectCategory")
    var projectCategory: String? = null

    @Expose
    @SerializedName("qid")
    var qid: String? = null

    @Expose
    @SerializedName("projectName")
    var projectName: String? = null

    @Expose
    @SerializedName("type")
    var type: String? = null

    @Expose
    @SerializedName("user_projects")
    var user_projects: ArrayList<UserProjectsRate>? = null

    @Expose
    @SerializedName("uuid")
    var uuid: String? = null


}