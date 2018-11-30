package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UserBackRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IUserBackModel {
    fun userBack(request: UserBackRequest): Observable<BooleanResponse>
}