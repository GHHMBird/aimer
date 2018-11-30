package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/27
 */
interface IUserNameLock {
    fun userNameLockSuccess(response: StringResponse)
    fun userNameLockFail(throwable: Throwable)
}