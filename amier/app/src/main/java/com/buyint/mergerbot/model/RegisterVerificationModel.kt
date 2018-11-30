package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.RegisterRequest
import com.buyint.mergerbot.dto.RegisterVerificationResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IRegisterVerificationModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/28
 */
class RegisterVerificationModel : IRegisterVerificationModel {
    override fun registerVerification(request: RegisterRequest): Observable<RegisterVerificationResponse> {
        return HttpHelper.registerVerification(request)
    }

}