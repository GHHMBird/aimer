package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/12
 */
interface IUpdatePrivacySetting {
    fun updatePrivacySettingSuccess(response: BooleanResponse)
    fun updatePrivacySettingFail(throwable: Throwable)
}