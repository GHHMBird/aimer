package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.QASpeakResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IQaSpeak {
    fun qaSpeakSuccess(response: QASpeakResponse)
    fun qaSpeakFail(throwable: Throwable)
}