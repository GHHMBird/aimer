package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/28
 */
interface IRegisterSetPasswordModel {
    fun registerSetPassword(password: String): Observable<BooleanResponse>
}