package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserProjectPerformanceList {

    fun getUserProjectPerformanceListSuccess(response: GetUserProjectPerformanceListResponse)
    fun getUserProjectPerformanceListFail(throwable: Throwable)

}