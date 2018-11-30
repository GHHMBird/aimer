package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PasswordChangePasswordRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPasswordChangePasswordModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/2
 */
class PasswordChangePasswordModel : IPasswordChangePasswordModel {
    override fun passwordChangePassword(request: PasswordChangePasswordRequest): Observable<BooleanResponse> {
        return HttpHelper.passwordChangePassword(request)
    }
}