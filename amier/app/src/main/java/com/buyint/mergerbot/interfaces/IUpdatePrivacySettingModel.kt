package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdatePrivacySettingRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/12
 */
interface IUpdatePrivacySettingModel {

    fun updatePrivacySetting(request: UpdatePrivacySettingRequest): Observable<BooleanResponse>

}