package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface ISetAttachMessageModel {
    fun setAttachMessage(string: String): Observable<BooleanResponse>
}