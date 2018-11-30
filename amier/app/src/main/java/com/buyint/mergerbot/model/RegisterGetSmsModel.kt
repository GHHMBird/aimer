package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IRegisterGetSmsModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/28
 */
class RegisterGetSmsModel : IRegisterGetSmsModel {
    override fun registerGetSms(type: String, phone: String): Observable<SmsResponse> {
        return HttpHelper.getRegisterSms(type, phone)
    }
}