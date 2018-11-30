package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse

/**
 * Created by huheming on 2018/6/20
 */
interface IPasswordLogin {

    fun passwordLoginSuccess(response:LoginResponse)

    fun passwordLoginFail(throwable: Throwable)
}