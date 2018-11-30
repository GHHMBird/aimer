package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateUserAllInfoRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/12
 */
interface IUpdateAllUserInfoModel {

    fun updateAllUserInfo(request:UpdateUserAllInfoRequest):Observable<BooleanResponse>

}