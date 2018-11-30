package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.RegisterRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISmsLoginModel
import io.reactivex.Observable


/**
 * Created by huheming on 2018/6/20
 */
class SmsLoginModel : ISmsLoginModel {
    override fun smsLogin(request: RegisterRequest): Observable<LoginResponse> {
        return HttpHelper.smsLogin(request)
    }
}