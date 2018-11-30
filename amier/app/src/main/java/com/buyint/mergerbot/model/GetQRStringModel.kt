package com.buyint.mergerbot.model

import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetQRStringModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/21
 */
class GetQRStringModel : IGetQRStringModel {
    override fun getQRString(): Observable<String> {
        return HttpHelper.getQRString()
    }
}