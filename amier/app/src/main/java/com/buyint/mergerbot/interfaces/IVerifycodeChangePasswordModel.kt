package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/2
 */
interface IVerifycodeChangePasswordModel {
    fun verifycodeChangePassword(request: VerifyCodeChangePasswordRequest): Observable<BooleanResponse>
}