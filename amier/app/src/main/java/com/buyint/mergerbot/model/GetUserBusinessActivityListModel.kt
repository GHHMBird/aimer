package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class GetUserBusinessActivityListModel : IGetUserBusinessActivityListModel {
    override fun getUserBusinessActivityList(request: GetUserProjectPerformanceListRequest): Observable<GetUserBusinessActivityListResponse> {
        return HttpHelper.getUserBusinessActivityList(request)
    }
}