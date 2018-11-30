package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IDeleteUserProjectPerformanceModel {
    fun deleteUserProjectPerformance(id: String): Observable<BooleanResponse>
}