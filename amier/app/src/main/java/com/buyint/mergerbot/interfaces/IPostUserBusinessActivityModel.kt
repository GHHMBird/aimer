package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import com.buyint.mergerbot.dto.StringResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IPostUserBusinessActivityModel {
    fun postUserBusinessActivity(request: PutUserBusinessActivityRequest): Observable<StringResponse>
}