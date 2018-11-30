package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LoginModel
import com.buyint.mergerbot.dto.PostUserInfoType3Request
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/28
 */
interface IPostUserInfoType3Model {
    fun postUserInfoType3(request: PostUserInfoType3Request): Observable<LoginModel>
}