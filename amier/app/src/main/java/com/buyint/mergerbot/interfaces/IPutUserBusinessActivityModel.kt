package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IPutUserBusinessActivityModel {
    fun putUserBusinessActivity(request: PutUserBusinessActivityRequest): Observable<BooleanResponse>
}