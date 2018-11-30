package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPostUserProjectPerformanceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class PostUserProjectPerformanceModel : IPostUserProjectPerformanceModel {
    override fun postUserProjectPerformance(request: PutUserProjectPerformanceRequest): Observable<StringResponse> {
        return HttpHelper.postUserProjectPerformance(request)
    }

}