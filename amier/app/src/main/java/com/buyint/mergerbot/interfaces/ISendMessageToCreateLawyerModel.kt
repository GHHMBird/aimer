package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.SendMessageToCreateLawyerRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/24
 */
interface ISendMessageToCreateLawyerModel {
    fun sendMessageToCreateLawyer(request: SendMessageToCreateLawyerRequest): Observable<String>
}