package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AccountVerificationVerifyMessageRequest
import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/3
 */
interface IAccountVerificationVerifyMessageModel {
    fun accountVerificationVerifyMessage(request: AccountVerificationVerifyMessageRequest): Observable<BooleanResponse>
}