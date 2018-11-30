package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetAttachMessageResponse

/**
 * Created by huheming on 2018/7/23
 */
interface IGetAttachMessage {
    fun getAttachMessageSuccess(response: GetAttachMessageResponse)
    fun getAttachMessageFail(throwable: Throwable)
}