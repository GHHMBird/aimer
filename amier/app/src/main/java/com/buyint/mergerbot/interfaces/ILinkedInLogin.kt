package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse

/**
 * Created by huheming on 2018/6/20
 */
interface ILinkedInLogin {

    fun linkedInLoginSuccess(response: LoginResponse)

    fun linkedInLoginFail(throwable: Throwable)
}