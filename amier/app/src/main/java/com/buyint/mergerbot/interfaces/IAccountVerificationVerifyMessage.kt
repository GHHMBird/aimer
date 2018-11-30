package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/8/3
 */
interface IAccountVerificationVerifyMessage {
    fun accountVerificationVerifyMessageSuccess(response: BooleanResponse)
    fun accountVerificationVerifyMessageFail(throwable: Throwable)
}