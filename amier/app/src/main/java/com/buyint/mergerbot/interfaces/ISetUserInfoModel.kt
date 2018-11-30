package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UserNikeAndIntentionRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface ISetUserInfoModel {
    fun setUserInfo(request: UserNikeAndIntentionRequest):Observable<BooleanResponse>
}