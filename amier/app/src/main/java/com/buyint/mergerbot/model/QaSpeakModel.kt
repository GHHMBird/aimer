package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.QASpeakRequest
import com.buyint.mergerbot.dto.QASpeakResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IQaSpeakModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class QaSpeakModel:IQaSpeakModel {
    override fun qaSpeak(request: QASpeakRequest): Observable<QASpeakResponse> {
        return HttpHelper.qaSpeak(request)
    }
}