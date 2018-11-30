package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IRegisterSetPasswordModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/28
 */
class RegisterSetPasswordModel : IRegisterSetPasswordModel {
    override fun registerSetPassword(password: String): Observable<BooleanResponse> {
        return HttpHelper.setPassword(password)
    }
}