package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IMatchCompany {
    fun matchCompanySuccess(response: MatchCompanyResponse)
    fun matchCompanyFail(throwable: Throwable)
}