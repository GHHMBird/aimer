package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IPostUserBusinessActivity {
    fun postUserBusinessActivitySuccess(response: StringResponse)
    fun postUserBusinessActivityFail(throwable: Throwable)
}