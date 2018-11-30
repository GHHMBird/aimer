package com.buyint.mergerbot.interfaces

/**
 * Created by huheming on 2018/8/21
 */
interface IGetQRString {
    fun getQRStringSuccess(text: String)
    fun getQRStringFail(throwable: Throwable)
}