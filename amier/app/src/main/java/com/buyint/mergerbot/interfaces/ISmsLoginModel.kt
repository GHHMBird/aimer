package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.RegisterRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
interface ISmsLoginModel {

    fun smsLogin(request: RegisterRequest): Observable<LoginResponse>
}