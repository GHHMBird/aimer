package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface IPutEducationExperienceModel {
    fun putEducationExperience(request: AddEducationExperienceRequest):Observable<BooleanResponse>
}