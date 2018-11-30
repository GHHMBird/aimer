package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPutUserProjectPerformanceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class PutUserProjectPerformanceModel : IPutUserProjectPerformanceModel {
    override fun putUserProjectPerformance(request: PutUserProjectPerformanceRequest): Observable<BooleanResponse> {
        return HttpHelper.putUserProjectPerformance(request)
    }
}