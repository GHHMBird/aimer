package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.SendMessageToCreateLawyerRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISendMessageToCreateLawyerModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/24
 */
class SendMessageToCreateLawyerModel : ISendMessageToCreateLawyerModel {
    override fun sendMessageToCreateLawyer(request: SendMessageToCreateLawyerRequest): Observable<String> {
        return HttpHelper.sendMessageToCreateLawyer(request)
    }
}