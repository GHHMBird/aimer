package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IMatchCompanyMorePage {
    fun matchCompanyMorePageSuccess(response: MatchCompanyMorePageResponse)
    fun matchCompanyMorePageFail(throwable: Throwable)
}