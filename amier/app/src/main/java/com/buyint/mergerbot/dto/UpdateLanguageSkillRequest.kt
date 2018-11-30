package com.buyint.mergerbot.dto

import com.buyint.mergerbot.http.RequestData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/27
 */
class UpdateLanguageSkillRequest : RequestData(), Serializable {
    @Expose
    @SerializedName("languageCompetence")
    var languageCompetence: ArrayList<String>? = null

    @Expose
    @SerializedName("otherLanguageCompetence")
    var otherLanguageCompetence: ArrayList<String>? = null


}