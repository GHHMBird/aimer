package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/26
 */
interface IUserAndCompanyNameAutoCompleteModel {
    fun userAndCompanyNameAutoComplete(userName: String, companyName: String): Observable<UserAndCompanyNameAutoCompleteResponse>
}