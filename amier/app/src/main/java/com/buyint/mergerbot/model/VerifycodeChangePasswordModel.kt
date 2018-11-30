package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IVerifycodeChangePasswordModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/2
 */
class VerifycodeChangePasswordModel : IVerifycodeChangePasswordModel {
    override fun verifycodeChangePassword(request: VerifyCodeChangePasswordRequest): Observable<BooleanResponse> {
        return HttpHelper.verifycodeChangePassword(request)
    }
}