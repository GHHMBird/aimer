package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UserBackRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUserBackModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class UserBackModel : IUserBackModel {
    override fun userBack(request: UserBackRequest): Observable<BooleanResponse> {
        return HttpHelper.userBack(request)
    }
}