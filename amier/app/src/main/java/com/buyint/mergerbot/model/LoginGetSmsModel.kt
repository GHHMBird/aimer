package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ILoginGetSmsModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
class LoginGetSmsModel :ILoginGetSmsModel{

    override fun loginGetSms(type: String, phone: String): Observable<SmsResponse> {
        return HttpHelper.getSms(type,phone)
    }

}