package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPutEducationExperienceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class PutEducationExperienceModel:IPutEducationExperienceModel {
    override fun putEducationExperience(request: AddEducationExperienceRequest): Observable<BooleanResponse> {
        return HttpHelper.putEducationExperience(request)
    }
}