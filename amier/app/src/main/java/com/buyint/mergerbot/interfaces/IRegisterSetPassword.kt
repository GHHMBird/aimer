package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/6/28
 */
interface IRegisterSetPassword {
    fun registerSetPasswordSuccess(response: BooleanResponse)

    fun registerSetPasswordFail(throwable: Throwable)
}