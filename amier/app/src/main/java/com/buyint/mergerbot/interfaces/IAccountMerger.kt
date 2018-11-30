package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginResponse

/**
 * Created by huheming on 2018/7/4
 */
interface IAccountMerger {
    fun accountMergerSuccess(response: LoginResponse)
    fun accountMergerFail(throwable: Throwable)

}