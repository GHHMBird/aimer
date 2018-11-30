package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse

/**
 * Created by huheming on 2018/8/8
 */
interface IGetMyMatchList {

    fun getMyMatchListSuccess(response: MatchCompanyMorePageResponse)

    fun getMyMatchListFail(throwable: Throwable)

    fun getMyMatchListMoreSuccess(response: MatchCompanyMorePageResponse)

    fun getMyMatchListMoreFail(throwable: Throwable)
}