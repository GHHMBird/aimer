package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.LoginModel
import com.buyint.mergerbot.dto.PostUserInfoType3Request
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPostUserInfoType3Model
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/28
 */
class PostUserInfoType3Model : IPostUserInfoType3Model {
    override fun postUserInfoType3(request: PostUserInfoType3Request): Observable<LoginModel> {
        return HttpHelper.postUserInfoType3(request)
    }
}