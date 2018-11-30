package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.SmsResponse

/**
 * Created by huheming on 2018/6/28
 */
interface IRegisterGetSms {
    fun registerGetSmsSuccess(response: SmsResponse)

    fun registerGetSmsFail(throwable: Throwable)
}