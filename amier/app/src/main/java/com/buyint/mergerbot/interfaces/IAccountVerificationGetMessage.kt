package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AccountVerificationGetMessageResponse

/**
 * Created by huheming on 2018/8/3
 */
interface IAccountVerificationGetMessage {
    fun accountVerificationGetMessageSuccess(response: AccountVerificationGetMessageResponse)
    fun accountVerificationGetMessageFail(throwable: Throwable)
}