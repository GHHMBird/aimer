package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.PasswordLoginRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
interface IPasswordLoginModel {
    fun passwordLogin(request: PasswordLoginRequest): Observable<LoginResponse>
}