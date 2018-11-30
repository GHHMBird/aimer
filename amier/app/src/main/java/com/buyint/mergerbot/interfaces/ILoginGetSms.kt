package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.SmsResponse

/**
 * Created by huheming on 2018/6/20
 */
interface ILoginGetSms {

    fun loginGetMsmSuccess(response: SmsResponse)

    fun loginGetMsmFail(throwable: Throwable)
}