package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetAttachMessageResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetAttachMessageModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class GetAttachMessageModel : IGetAttachMessageModel {
    override fun getAttachMessage(): Observable<GetAttachMessageResponse> {
        return HttpHelper.getAttachMessage()
    }

}