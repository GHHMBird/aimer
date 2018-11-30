package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.SmsResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/28
 */
interface IRegisterGetSmsModel {
    fun registerGetSms(type: String, phone: String): Observable<SmsResponse>
}