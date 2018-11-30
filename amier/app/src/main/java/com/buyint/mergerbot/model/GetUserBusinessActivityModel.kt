package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class GetUserBusinessActivityModel : IGetUserBusinessActivityModel {
    override fun getUserBusinessActivity(id: String): Observable<GetUserBusinessActivityResponse> {
        return HttpHelper.getUserBusinessActivity(id)
    }

}