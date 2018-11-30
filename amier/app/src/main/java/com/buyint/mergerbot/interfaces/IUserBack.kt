package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IUserBack {
    fun userBackSuccess(response: BooleanResponse)
    fun userBackFail(throwable: Throwable)
}