package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AccountVerificationGetMessageRequest
import com.buyint.mergerbot.dto.AccountVerificationGetMessageResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/3
 */
interface IAccountVerificationGetMessageModel {
    fun accountVerificationGetMessage(request: AccountVerificationGetMessageRequest): Observable<AccountVerificationGetMessageResponse>
}