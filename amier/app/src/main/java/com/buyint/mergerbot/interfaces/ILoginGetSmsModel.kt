package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.SmsResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */

interface ILoginGetSmsModel {
    fun loginGetSms(type: String, phone: String): Observable<SmsResponse>
}