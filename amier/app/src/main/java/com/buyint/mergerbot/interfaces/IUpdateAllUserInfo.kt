package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateUserAllInfoRequest

/**
 * Created by huheming on 2018/7/12
 */
interface IUpdateAllUserInfo {
    fun updateAllUserInfoSuccess(response: BooleanResponse,request: UpdateUserAllInfoRequest)
    fun updateAllUserInfoFail(throwable: Throwable)
}