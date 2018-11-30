package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UserNikeAndIntentionRequest

/**
 * Created by huheming on 2018/7/24
 */
interface ISetUserInfo {
    fun setUserInfoSuccess(response: BooleanResponse)
    fun setUserInfoFail(throwable: Throwable)
}