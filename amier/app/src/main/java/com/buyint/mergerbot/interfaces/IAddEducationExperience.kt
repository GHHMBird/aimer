package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/23
 */
interface IAddEducationExperience {
    fun addEducationExperienceSuccess(response: StringResponse)
    fun addEducationExperienceFail(throwable: Throwable)
}