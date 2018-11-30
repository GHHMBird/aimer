package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateUserAllInfoRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdateAllUserInfoModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/12
 */
class UpdateAllUserInfoModel:IUpdateAllUserInfoModel {
    override fun updateAllUserInfo(request: UpdateUserAllInfoRequest): Observable<BooleanResponse> {
        return HttpHelper.updateAllUserInfo(request)
    }
}