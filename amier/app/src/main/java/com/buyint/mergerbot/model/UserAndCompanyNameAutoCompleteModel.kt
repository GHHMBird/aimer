package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoCompleteModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/26
 */
class UserAndCompanyNameAutoCompleteModel : IUserAndCompanyNameAutoCompleteModel {
    override fun userAndCompanyNameAutoComplete(userName: String, companyName: String): Observable<UserAndCompanyNameAutoCompleteResponse> {
        return HttpHelper.userAndCompanyNameAutoComplete(userName, companyName)
    }
}