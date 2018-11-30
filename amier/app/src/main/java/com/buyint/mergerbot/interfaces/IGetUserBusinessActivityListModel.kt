package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserBusinessActivityListModel {
    fun getUserBusinessActivityList(request: GetUserProjectPerformanceListRequest):Observable<GetUserBusinessActivityListResponse>
}