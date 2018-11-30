package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPutUserBusinessActivityModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class PutUserBusinessActivityModel : IPutUserBusinessActivityModel {
    override fun putUserBusinessActivity(request: PutUserBusinessActivityRequest): Observable<BooleanResponse> {
        return HttpHelper.putUserBusinessActivity(request)
    }

}