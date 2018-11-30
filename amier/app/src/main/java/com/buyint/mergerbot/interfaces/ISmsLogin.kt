package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse

/**
 * Created by huheming on 2018/6/20
 */
interface ISmsLogin {

    fun smsLoginSuccess(loginResponse: LoginResponse)

    fun smsLoginFail(throwable: Throwable)
}