package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginModel

/**
 * Created by huheming on 2018/8/28
 */
interface IPostUserInfoType3 {
    fun postUserInfoType3Success(response: LoginModel)
    fun postUserInfoType3Fail(throwable: Throwable)
}