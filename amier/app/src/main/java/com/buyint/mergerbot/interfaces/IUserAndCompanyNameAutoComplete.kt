package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse

/**
 * Created by huheming on 2018/7/26
 */
interface IUserAndCompanyNameAutoComplete {
    fun userAndCompanyNameAutoCompleteUserNameSuccess(response: UserAndCompanyNameAutoCompleteResponse)
    fun userAndCompanyNameAutoCompleteUserCompanyNameSuccess(response: UserAndCompanyNameAutoCompleteResponse)
    fun userAndCompanyNameAutoCompleteUserFail(throwable: Throwable)
}