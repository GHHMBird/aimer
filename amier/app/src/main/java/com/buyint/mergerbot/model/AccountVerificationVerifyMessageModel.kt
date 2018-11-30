package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.AccountVerificationVerifyMessageRequest
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IAccountVerificationVerifyMessageModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/3
 */
class AccountVerificationVerifyMessageModel : IAccountVerificationVerifyMessageModel {
    override fun accountVerificationVerifyMessage(request: AccountVerificationVerifyMessageRequest): Observable<BooleanResponse> {
        return HttpHelper.accountVerificationVerifyMessage(request)
    }

}