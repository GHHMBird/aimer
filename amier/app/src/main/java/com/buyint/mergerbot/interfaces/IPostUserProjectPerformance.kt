package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IPostUserProjectPerformance {
    fun postUserProjectPerformanceSuccess(response: StringResponse)
    fun postUserProjectPerformanceFail(throwable: Throwable)
}