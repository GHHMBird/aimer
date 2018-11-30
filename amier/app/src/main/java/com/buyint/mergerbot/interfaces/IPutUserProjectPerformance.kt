package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IPutUserProjectPerformance {
    fun putUserProjectPerformanceSuccess(response: BooleanResponse)
    fun putUserProjectPerformanceFail(throwable: Throwable)
}