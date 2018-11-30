package com.buyint.mergerbot.interfaces

/**
 * Created by huheming on 2018/8/21
 */
interface ISendQRString {
    fun sendQRStringSuccess(boolean: Boolean)
    fun sendQRStringFail(throwable: Throwable)
}