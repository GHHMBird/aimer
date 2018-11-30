package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserProjectPerformanceResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserProjectPerformance {
    fun getUserProjectPerformanceSuccess(response: GetUserProjectPerformanceResponse)
    fun getUserProjectPerformanceFail(throwable: Throwable)
}