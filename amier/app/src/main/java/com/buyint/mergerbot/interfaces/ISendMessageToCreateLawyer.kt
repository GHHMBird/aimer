package com.buyint.mergerbot.interfaces

/**
 * Created by huheming on 2018/8/24
 */
interface ISendMessageToCreateLawyer {
    fun sendMessageToCreateLawyerSuccess(text: String)
    fun sendMessageToCreateLawyerFail(throwable: Throwable)
}