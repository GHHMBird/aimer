package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.LinkedinRequest
import com.buyint.mergerbot.dto.LoginResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/6/20
 */
interface ILinkedInLoginModel {

    fun LinkedInLogin(request: LinkedinRequest): Observable<LoginResponse>
}