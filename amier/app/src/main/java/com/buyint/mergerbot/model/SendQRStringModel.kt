package com.buyint.mergerbot.model

import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISendQRStringModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/21
 */
class SendQRStringModel : ISendQRStringModel {
    override fun sendQRString(text: String): Observable<Boolean> {
        return HttpHelper.sendQRString(text)
    }
}