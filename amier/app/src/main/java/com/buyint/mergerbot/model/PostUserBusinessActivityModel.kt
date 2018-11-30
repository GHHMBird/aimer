package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPostUserBusinessActivityModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class PostUserBusinessActivityModel : IPostUserBusinessActivityModel {
    override fun postUserBusinessActivity(request: PutUserBusinessActivityRequest): Observable<StringResponse> {
        return HttpHelper.postUserBusinessActivity(request)
    }

}