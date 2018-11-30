package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.AccountMergerRequest
import com.buyint.mergerbot.dto.LoginResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/4
 */
interface IAccountMergerModel {
    fun accountMerger(request: AccountMergerRequest): Observable<LoginResponse>
}