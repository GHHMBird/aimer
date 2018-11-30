package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IUserIconUpdate {
    fun userIconUpdateSuccess(response: StringResponse)
    fun userIconUpdateFail(throwable: Throwable)
}