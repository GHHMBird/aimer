package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/2
 */
interface IVerifycodeChangePassword {
    fun verifycodeChangePasswordSuccess(booleanResponse: BooleanResponse)
    fun verifycodeChangePasswordFail(throwable: Throwable)
}