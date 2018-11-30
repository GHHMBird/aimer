package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.AccountVerificationGetMessageRequest
import com.buyint.mergerbot.dto.AccountVerificationGetMessageResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessageModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationGetMessageModel : IAccountVerificationGetMessageModel {
    override fun accountVerificationGetMessage(request: AccountVerificationGetMessageRequest): Observable<AccountVerificationGetMessageResponse> {
        return HttpHelper.accountVerificationGetMessage(request)
    }
}