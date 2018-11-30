package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdatePrivacySettingRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdatePrivacySettingModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/12
 */
class UpdatePrivacySettingModel : IUpdatePrivacySettingModel {
    override fun updatePrivacySetting(request: UpdatePrivacySettingRequest): Observable<BooleanResponse> {
        return HttpHelper.updatePrivacySettingArchives(request)
    }
}