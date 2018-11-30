package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest
import com.buyint.mergerbot.dto.StringResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IPutUserProjectPerformanceModel {

    fun putUserProjectPerformance(request: PutUserProjectPerformanceRequest): Observable<BooleanResponse>

}