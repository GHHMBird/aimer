package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.PasswordLoginRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPasswordLoginModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
class PasswordLoginModel : IPasswordLoginModel {
    override fun passwordLogin(request: PasswordLoginRequest): Observable<LoginResponse> {
        return HttpHelper.passwordLogin(request)
    }
}