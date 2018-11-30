package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetAttachMessageResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface IGetAttachMessageModel {

    fun getAttachMessage(): Observable<GetAttachMessageResponse>

}