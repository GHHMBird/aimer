package com.buyint.mergerbot.interfaces

import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/21
 */
interface IGetQRStringModel {
    fun getQRString(): Observable<String>
}