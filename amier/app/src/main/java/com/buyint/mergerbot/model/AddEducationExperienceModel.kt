package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IAddEducationExperienceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class AddEducationExperienceModel : IAddEducationExperienceModel {
    override fun addEducationExperience(request: AddEducationExperienceRequest): Observable<StringResponse> {
        return HttpHelper.addEducationExperience(request)
    }
}