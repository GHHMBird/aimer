package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PasswordChangePasswordRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/2
 */
interface IPasswordChangePasswordModel {
    fun passwordChangePassword(request: PasswordChangePasswordRequest): Observable<BooleanResponse>
}