package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetUserProjectPerformanceResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class GetUserProjectPerformanceModel : IGetUserProjectPerformanceModel {
    override fun getUserProjectPerformance(id: String): Observable<GetUserProjectPerformanceResponse> {
        return HttpHelper.getUserProjectPerformance(id)
    }
}