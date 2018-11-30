package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISetAttachMessageModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class SetAttachMessageModel:ISetAttachMessageModel {
    override fun setAttachMessage(string: String): Observable<BooleanResponse> {
        return HttpHelper.setAttachMessage(string)
    }
}