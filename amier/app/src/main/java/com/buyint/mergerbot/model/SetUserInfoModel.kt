package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UserNikeAndIntentionRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISetUserInfoModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class SetUserInfoModel : ISetUserInfoModel {
    override fun setUserInfo(request: UserNikeAndIntentionRequest): Observable<BooleanResponse> {
        return HttpHelper.setUserInfo(request)
    }
}