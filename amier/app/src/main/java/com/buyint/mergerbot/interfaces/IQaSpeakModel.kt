package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.QASpeakRequest
import com.buyint.mergerbot.dto.QASpeakResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IQaSpeakModel {
    fun qaSpeak(request: QASpeakRequest): Observable<QASpeakResponse>
}