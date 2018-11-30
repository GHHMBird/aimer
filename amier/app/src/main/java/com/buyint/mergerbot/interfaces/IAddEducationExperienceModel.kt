package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.dto.StringResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface IAddEducationExperienceModel {
    fun addEducationExperience(request: AddEducationExperienceRequest): Observable<StringResponse>
}