package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/2
 */
interface IPasswordChangePassword {
    fun passwordChangePasswordSuccess(booleanResponse: BooleanResponse)
    fun passwordChangePasswordFail(throwable: Throwable)
}