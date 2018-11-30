package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/23
 */
interface IPutEducationExperience {
    fun putEducationExperienceSuccess(response: BooleanResponse)
    fun putEducationExperienceFail(throwable: Throwable)
}