package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserProjectPerformanceListModel {

    fun getUserProjectPerformanceList(request: GetUserProjectPerformanceListRequest):Observable<GetUserProjectPerformanceListResponse>

}