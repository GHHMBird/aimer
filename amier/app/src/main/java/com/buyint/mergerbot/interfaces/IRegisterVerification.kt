package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.RegisterVerificationResponse

/**
 * Created by huheming on 2018/6/28
 */
interface IRegisterVerification {
    fun registerVerificationSuccess(response: RegisterVerificationResponse)
    fun registerVerificationFail(throwable: Throwable)
}