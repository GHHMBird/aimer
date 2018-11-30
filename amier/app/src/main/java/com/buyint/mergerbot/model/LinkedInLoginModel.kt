package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.LinkedinRequest
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ILinkedInLoginModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
class LinkedInLoginModel : ILinkedInLoginModel {
    override fun LinkedInLogin(request: LinkedinRequest): Observable<LoginResponse> {
        return HttpHelper.linkedinRegister(request)
    }
}