package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.AccountMergerRequest
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IAccountMergerModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/4
 */
class AccountMergerModel : IAccountMergerModel {
    override fun accountMerger(request: AccountMergerRequest): Observable<LoginResponse> {
        return HttpHelper.accountMerger(request)
    }
}