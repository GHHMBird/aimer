package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/7/23
 */
class GetAttachMessageModel : Serializable {


    @Expose
    @SerializedName("attachMessage")
    var attachMessage: String? = null


    @Expose
    @SerializedName("educationExperience")
    var educationExperience: ArrayList<AddEducationExperienceRequest>? = null


    @Expose
    @SerializedName("industryClassification")
    var industryClassification: ArrayList<CodeNameBean>? = null


    @Expose
    @SerializedName("languageCompetence")
    var languageCompetence: ArrayList<String>? = null

    @Expose
    @SerializedName("otherLanguageCompetence")
    var otherLanguageCompetence: ArrayList<String>? = null


}